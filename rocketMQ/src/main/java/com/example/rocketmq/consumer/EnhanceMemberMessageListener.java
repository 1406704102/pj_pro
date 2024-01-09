package com.example.rocketmq.consumer;

import com.example.rocketmq.config.EnhanceMessageHandler;
import com.example.rocketmq.message.RocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "enhance_consumer_group",
        topic = "pj_topic_test",
//        selectorExpression = "*",
        consumeThreadMax = 5 //默认是64个线程并发消息，配置 consumeThreadMax 参数指定并发消费线程数，避免太大导致资源不够
)
public class EnhanceMemberMessageListener extends EnhanceMessageHandler<RocketMessage> implements RocketMQListener<RocketMessage> {

    @Override
    protected void handleMessage(RocketMessage message) throws Exception {
        // 此时这里才是最终的业务处理，代码只需要处理资源类关闭异常，其他的可以交给父类重试
        System.out.println("业务消息处理:"+message.getMessage());
    }

    @Override
    protected void handleMaxRetriesExceeded(RocketMessage message) {
        // 当超过指定重试次数消息时此处方法会被调用
        // 生产中可以进行回退或其他业务操作
        log.error("消息消费失败，请执行后续处理");
    }


    /**
     * 是否执行重试机制
     */
    @Override
    protected boolean isRetry() {
        return true;
    }

    @Override
    protected boolean throwException() {
        // 是否抛出异常，false搭配retry自行处理异常
        return false;
    }
  
    @Override
    protected boolean filter(RocketMessage message) {
        if (message.getMessage().equals("123")) {
            return true;
        }
        // 消息过滤
        return false;
    }

    /**
     * 监听消费消息，不需要执行业务处理，委派给父类做基础操作，父类做完基础操作后会调用子类的实际处理类型
     */
    @Override
    public void onMessage(RocketMessage memberMessage) {
        super.dispatchMessage(memberMessage);
    }
}