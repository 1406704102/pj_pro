package com.example.rocketmq.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
public class RocketTopic {

    @Value("${rocket.topic.topicTest}")
    private String topicTest;
    @Value("${rocket.topic.topicTestTag}")
    private String topicTestTag;
}
