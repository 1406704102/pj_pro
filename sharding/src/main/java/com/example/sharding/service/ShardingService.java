package com.example.sharding.service;


import com.example.sharding.entity.Sharding;

public interface ShardingService {

    void save();

    Sharding findById(Long id);
}
