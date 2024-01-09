package com.example.sharding.repository;

import com.example.sharding.entity.Sharding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShardingDao extends JpaRepository<Sharding,Long> {
}