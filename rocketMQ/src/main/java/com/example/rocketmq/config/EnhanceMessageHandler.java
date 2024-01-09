package com.example.rocketmq.config;

import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

@Slf4j
public abstract class EnhanceMessageHandler<T extends BaseMessage> {
    /**
     * 默认重试次数
     */
    private static final int MAX_RETRY_TIMES = EnhanceMessageConstant.MAX_TIMES;

    /**
     * 延时等级
     */
    private static final int DELAY_LEVEL = EnhanceMessageConstant.DELAY_LEVEL;


    @Resource
    private RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    @Resource
    private RocketEnhanceProperties rocketEnhanceProperties;

    /**
     * 消息处理
     *
     * @param message 待处理消息
     * @throws Exception 消费异常
     */
    protected abstract void handleMessage(T message) throws Exception;

    /**
     * 超过重试次数消息，需要启用isRetry
     *
     * @param message 待处理消息
     */
    protected abstract void handleMaxRetriesExceeded(T message);


    /**
     * 是否需要根据业务规则过滤消息，去重逻辑可以在此处处理
     *
     * @param message 待处理消息
     * @return true: 本次消息被过滤，false：不过滤
     */
    protected boolean filter(T message) {
        return false;
    }

    /**
     * 是否异常时重复发送
     *
     * @return true: 消息重试，false：不重试
     */
    protected abstract boolean isRetry();

    /**
     * 消费异常时是否抛出异常
     * 返回true，则由rocketmq机制自动重试
     * false：消费异常(如果没有开启重试则消息会被自动ack)
     */
    protected abstract boolean throwException();

    /**
     * 最大重试次数
     *
     * @return 最大重试次数
     */
    protected int getMaxRetryTimes() {
        return MAX_RETRY_TIMES;
    }

    /**
     * isRetry开启时，重新入队延迟时间
     * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @return 等级
     */
    protected int getDelayLevel() {
        return DELAY_LEVEL;
    }

    /**
     * 使用模板模式构建消息消费框架，可自由扩展或删减
     */
    public void dispatchMessage(T message) {
        // 基础日志记录被父类处理了
        log.info("消费者收到消息[{}]", JSONObject.toJSON(message));
        if (filter(message)) {
            log.info("消息id{}不满足消费条件，已过滤。", message.getKey());
            return;
        }
        // 超过最大重试次数时调用子类方法处理
        if (message.getRetryTimes() >= getMaxRetryTimes()) {
            handleMaxRetriesExceeded(message);
            return;
        }
        try {
//            int i = 1 / 0;
            long now = System.currentTimeMillis();
            handleMessage(message);
            long costTime = System.currentTimeMillis() - now;
            log.info("消息{}消费成功，耗时[{}ms]", message.getKey(), costTime);
        } catch (Exception e) {
            log.error("消息{}消费异常", message.getKey(), e);
            // 是捕获异常还是抛出，由子类决定
            if (throwException()) {
                //抛出异常，由DefaultMessageListenerConcurrently类处理
                throw new RuntimeException(e);
            }
            //此时如果不开启重试机制，则默认ACK了
            if (isRetry()) {
                handleRetry(message);
            }
        }
    }

    protected void handleRetry(T message) {
        // 获取子类RocketMQMessageListener注解拿到topic和tag
        RocketMQMessageListener annotation = this.getClass().getAnnotation(RocketMQMessageListener.class);
        if (annotation == null) {
            return;
        }
        //重新构建消息体
        String messageSource = message.getSource();
        if (!messageSource.startsWith(EnhanceMessageConstant.RETRY_PREFIX)) {
            message.setSource(EnhanceMessageConstant.RETRY_PREFIX + messageSource);
        }
        Integer retryTimes = message.getRetryTimes();
        message.setRetryTimes(retryTimes + 1);

        SendResult sendResult;

        try {
            // 如果消息发送不成功，则再次重新发送，如果发送异常则抛出由MQ再次处理(异常时不走延迟消息)
            int delayLevel = getDelayLevel() + retryTimes;
            sendResult = rocketMQEnhanceTemplate.send(annotation.topic(), "Retry_" + message.getRetryTimes(), message, delayLevel);
            log.info("重试发送延迟{}等级消息第 {} 次成功，消息[{}]", delayLevel,message.getRetryTimes(), message);
        } catch (Exception ex) {
            // 此处捕获之后，相当于此条消息被消息完成然后重新发送新的消息
            //由生产者直接发送
            throw new RuntimeException(ex);
        }
        // 发送失败的处理就是不进行ACK，由RocketMQ重试
        if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
            throw new RuntimeException("重试消息发送失败");
        }

    }

}