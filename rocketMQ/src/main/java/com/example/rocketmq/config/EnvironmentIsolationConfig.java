package com.example.rocketmq.config;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Configuration
public class EnvironmentIsolationConfig implements BeanPostProcessor {

    @Resource
    private RocketEnhanceProperties rocketEnhanceProperties;



    /**
     * 在装载Bean之前实现参数修改
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DefaultRocketMQListenerContainer){

            DefaultRocketMQListenerContainer container = (DefaultRocketMQListenerContainer) bean;
            //拼接Topic
            if(rocketEnhanceProperties.isEnabledIsolation() && StringUtils.hasText(rocketEnhanceProperties.getEnvironment())){
                container.setTopic(String.join("_", container.getTopic(),rocketEnhanceProperties.getEnvironment()));
            }
            return container;
        }
        return bean;
    }
}