package com.example.data.jpa.web.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.data.jpa.common.utils.PageResult;
import com.example.data.jpa.common.utils.PageUtil;
import com.example.data.jpa.common.utils.QueryHelp;
import com.example.data.jpa.web.domain.GoodsInfo;
import com.example.data.jpa.web.repository.GoodsInfoRepo;
import com.example.data.jpa.web.service.GoodsInfoService;
import com.example.data.jpa.web.service.dto.GoodsInfoDto;
import com.example.data.jpa.web.service.dto.GoodsInfoQuery;
import com.example.data.jpa.web.service.mapstruct.GoodsInfoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GoodsInfoServiceImpl implements GoodsInfoService {

    private final GoodsInfoRepo goodsInfoRepo;
    private final GoodsInfoMapper goodsInfoMapper;

    @Override
    public PageResult<GoodsInfoDto> queryPage(GoodsInfoQuery goodsInfoQuery, Pageable pageable) {
//        Page<GoodsInfo> page = goodsInfoRepo.findAll((root, query, cb) -> QueryHelp.getPredicate(root, goodsInfoQuery, cb), pageable);
        Page<GoodsInfo> page = goodsInfoRepo.findAll((Specification<GoodsInfo>) (root, query, criteriaBuilder) -> QueryHelp.getPredicate(root, goodsInfoQuery, criteriaBuilder), pageable);
//        List<GoodsInfo> all = goodsInfoRepo.findAll(new Specification<>() {
//            @Override
//            public Predicate toPredicate(Root<GoodsInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
////                Predicate id = criteriaBuilder.equal(root.get("id"), goodsInfoQuery.getId());
////                Predicate userName = criteriaBuilder.equal(root.get("goodsName"), goodsInfoQuery.getGoodsName());
//                Predicate goodsIdJoin = criteriaBuilder.equal(root.join("goodsInfoType", JoinType.LEFT).get("id"), 1744986663828545536L);
//
//
//                ArrayList<Object> objects = new ArrayList<>();
////                objects.add(id);
////                objects.add(userName);
//                objects.add(goodsIdJoin);
//
//                return criteriaBuilder.and(objects.toArray(new Predicate[objects.size()]));
//            }
//        });

        return PageUtil.toPage(page.map(goodsInfoMapper::toDto));
    }

    @Override
    public GoodsInfoDto save(GoodsInfo goodsInfo) {
        goodsInfo.setId(IdUtil.getSnowflakeNextId());
        GoodsInfo save = goodsInfoRepo.save(goodsInfo);
//        int i = 1 / 0;
        return goodsInfoMapper.toDto(save);
    }
    @Override
    @DS("master_2")
    public GoodsInfoDto saveTwo(GoodsInfo goodsInfo) {
        goodsInfo.setId(IdUtil.getSnowflakeNextId());
        GoodsInfo save = goodsInfoRepo.save(goodsInfo);
//        int i = 1 / 0;
        return goodsInfoMapper.toDto(save);
    }


    @Override
    public GoodsInfoDto findByGoodsNameInOne(String goodsName) {
        GoodsInfo goodsInfo = goodsInfoRepo.findAllByGoodsName(goodsName);
        return goodsInfoMapper.toDto(goodsInfo);
    }

    @Override
    @DS("master_2")
    public GoodsInfoDto findByGoodsNameInTwo(String goodsName) {
        GoodsInfo goodsInfo = goodsInfoRepo.findAllByGoodsName(goodsName);
        return goodsInfoMapper.toDto(goodsInfo);
    }

}
