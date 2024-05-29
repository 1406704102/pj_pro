package com.example.data.jpa.web.service;

import com.example.data.jpa.common.utils.PageResult;
import com.example.data.jpa.web.domain.GoodsInfo;
import com.example.data.jpa.web.service.dto.GoodsInfoDto;
import com.example.data.jpa.web.service.dto.GoodsInfoQuery;
import org.springframework.data.domain.Pageable;

public interface GoodsInfoService {

    PageResult<GoodsInfoDto> queryPage(GoodsInfoQuery goodsInfoQuery, Pageable pageable);

    GoodsInfoDto save(GoodsInfo goodsInfo);


    GoodsInfoDto saveTwo(GoodsInfo goodsInfo);

    GoodsInfoDto findByGoodsNameInOne(String goodsName);

    GoodsInfoDto findByGoodsNameInTwo(String goodsName);
}
