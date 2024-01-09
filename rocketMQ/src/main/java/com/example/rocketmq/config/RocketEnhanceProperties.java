package com.example.rocketmq.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Data
@Configuration
public class RocketEnhanceProperties {

    @Value("${rocketmq.enhance.enabledIsolation:true}")
    private boolean enabledIsolation;
    @Value("${rocketmq.enhance.environment:''}")
    private String environment;


    /**
     * 根据系统上下文自动构建隔离后的topic
     * 构建目的地
     */
    public String buildDestination(String topic, String tag) {
        topic = reBuildTopic(topic);
        return topic + ":" + tag;
    }

    /**
     * 根据环境重新隔离topic
     *
     * @param topic 原始topic
     */
    private String reBuildTopic(String topic) {
        if (enabledIsolation && StringUtils.hasText(environment)) {
            return topic + "_" + environment;
        }
        return topic;
    }

}