package com.example.rocketmq.message;


import com.example.rocketmq.config.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class RocketMessage extends BaseMessage {
    private long id;

    private String message;
}
