package com.example.data.jpa.web.service.dto;

import com.example.data.jpa.common.annotation.Query;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

@Data
public class GoodsInfoQuery {

    @Query
    private Long id;

    @Query(type = Query.Type.INNER_LIKE)
    private String goodsName;


    @Query(type = Query.Type.NOT_NULL)
    private String createBy;


    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
