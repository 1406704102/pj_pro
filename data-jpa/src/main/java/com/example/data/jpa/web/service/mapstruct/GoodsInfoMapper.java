package com.example.data.jpa.web.service.mapstruct;

import com.example.data.jpa.common.base.BaseMapper;
import com.example.data.jpa.web.domain.GoodsInfo;
import com.example.data.jpa.web.service.dto.GoodsInfoDto;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapper;

/*
 * @Author PangJie___
 * @Description @Mapper(componentModel = "spring", uses = {XX.class, ZZ.class}使用了那些类 , unmappedTargetPolicy = ReportingPolicy.IGNORE)
 * @Date 11:13 2024-01-10
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoodsInfoMapper extends BaseMapper<GoodsInfoDto, GoodsInfo> {

}