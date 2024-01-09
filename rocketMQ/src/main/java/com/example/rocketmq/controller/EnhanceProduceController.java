package com.example.rocketmq.controller;

import com.example.rocketmq.config.RocketMQEnhanceTemplate;
import com.example.rocketmq.config.RocketTopic;
import com.example.rocketmq.message.RocketMessage;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("enhance")
@Slf4j
@AllArgsConstructor
public class EnhanceProduceController {

    //注入增强后的模板，可以自动实现环境隔离，日志记录
    private final RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    private final RocketTopic rocketTopic;
    /**
     * 发送实体消息
     */
    @GetMapping("/member")
    public SendResult member(@RequestParam("msg") String msg) {
        String key = UUID.randomUUID().toString();

        RocketMessage message = new RocketMessage();

        message.setKey(key);
        message.setSource("test");
        message.setMessage(msg);
        return rocketMQEnhanceTemplate.send(rocketTopic.getTopicTest(), rocketTopic.getTopicTestTag(), message);
    }

}