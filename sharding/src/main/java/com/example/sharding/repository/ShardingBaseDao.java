package com.example.sharding.repository;

import com.example.sharding.entity.ShardingBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShardingBaseDao extends JpaRepository<ShardingBase, Long>, JpaSpecificationExecutor<ShardingBase> {
}