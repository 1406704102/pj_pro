package com.example.sharding.service;


import com.example.sharding.entity.Sharding;
import com.example.sharding.entity.ShardingBase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ShardingService {

    void save();

    Sharding findById(Long id);

    ShardingBase findBaseById(Long id);

    Slice<Sharding> query(Pageable pageable);


    List<Sharding> findBySql(int id);
}
