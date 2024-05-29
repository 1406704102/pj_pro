package com.example.sharding.service;


import com.example.sharding.entity.Sharding;
import com.example.sharding.entity.ShardingBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShardingService {

    void save();

    Sharding findById(Long id);

    ShardingBase findBaseById(Long id);

    Page<Sharding> query(Pageable pageable);
}
