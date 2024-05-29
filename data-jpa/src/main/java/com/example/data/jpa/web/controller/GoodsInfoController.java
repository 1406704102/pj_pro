package com.example.data.jpa.web.controller;


import com.example.data.jpa.web.domain.GoodsInfo;
import com.example.data.jpa.web.service.GoodsInfoService;
import com.example.data.jpa.web.service.dto.GoodsInfoDto;
import com.example.data.jpa.web.service.dto.GoodsInfoQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@AllArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsInfoController {

    private final GoodsInfoService goodsInfoService;

    @GetMapping
    public ResponseEntity<Object> queryPage(GoodsInfoQuery goodsInfoQuery, Pageable pageable) {
        return new ResponseEntity<>(goodsInfoService.queryPage(goodsInfoQuery, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> save(@RequestBody GoodsInfo dto) {
        GoodsInfoDto save = goodsInfoService.save(dto);
        GoodsInfoDto save2 = goodsInfoService.saveTwo(dto);
        return new ResponseEntity<>("save2", HttpStatus.CREATED);
    }

    @GetMapping("/{goodsName}")
    public ResponseEntity<Object> get(@PathVariable String goodsName) {
        ArrayList<GoodsInfoDto> list = new ArrayList<>();
        GoodsInfoDto one = goodsInfoService.findByGoodsNameInOne(goodsName);
        list.add(one);
        GoodsInfoDto two = goodsInfoService.findByGoodsNameInTwo(goodsName);
        list.add(two);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
