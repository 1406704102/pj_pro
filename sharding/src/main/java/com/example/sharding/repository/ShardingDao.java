package com.example.sharding.repository;

import com.example.sharding.entity.Sharding;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShardingDao extends  JpaRepository<Sharding, Long>, JpaSpecificationExecutor<Sharding> {

    Slice<Sharding> findAllBy(Pageable pageable);
    Slice<Sharding> findAllByNameLike(String name,Pageable pageable);

    List<Sharding> findAllByIdBetween(Long idSmall, Long idLarge);

    @Query("select max(id) from Sharding")
    Long getTopId();

}