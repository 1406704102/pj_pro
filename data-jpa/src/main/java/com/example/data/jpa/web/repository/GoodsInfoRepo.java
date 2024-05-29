package com.example.data.jpa.web.repository;

import com.example.data.jpa.web.domain.GoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsInfoRepo extends JpaRepository<GoodsInfo, Long>, JpaSpecificationExecutor<GoodsInfo> {

    GoodsInfo findAllByGoodsName(String goodsName);
}
