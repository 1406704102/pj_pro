package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RocketEntity {


    private Integer id;

    private String goodsName;

    private Integer goodsStock;

    private Timestamp createTime;
}
