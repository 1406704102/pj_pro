package com.example.sharding.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.sharding.config.CriteriaNoCountDao;
import com.example.sharding.entity.Sharding;
import com.example.sharding.entity.ShardingBase;
import com.example.sharding.repository.ShardingBaseDao;
import com.example.sharding.repository.ShardingDao;
import com.example.sharding.service.ShardingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShardingServiceImpl implements ShardingService {

    private final ShardingDao shardingDao;
    private final ShardingBaseDao shardingBaseDao;
    private final CriteriaNoCountDao criteriaNoCountDao;
    @Override
    public void save() {
//        LongStream.range(1, 31).forEach(f -> {
//            Sharding sharding = new Sharding();
//            long id = IdUtil.getSnowflake().nextId();
//            sharding.setId(f);
//            sharding.setName("java");
//            sharding.setUserId(id);
//            sharding.setStatus("Normal");
//            shardingDao.save(sharding);
//            System.out.println(id % 3);
//            System.out.println(id % 2 + 1 + "->" + id);
//        });

        ShardingBase shardingBase = new ShardingBase();
        shardingBase.setId(IdUtil.getSnowflake().nextId());
        shardingBase.setName("java");
        shardingBase.setUserId(IdUtil.getSnowflake().nextId());
        shardingBase.setStatus("Normal");
        shardingBaseDao.save(shardingBase);
    }

    @Override
    public Sharding findById(Long id) {
        return shardingDao.findById(id).orElseGet(Sharding::new);
    }
    @Override
    public ShardingBase findBaseById(Long id) {
        return shardingBaseDao.findById(id).orElseGet(ShardingBase::new);
    }

    @Override
    public Slice<Sharding> query(Pageable pageable) {
//        Slice<Sharding> all = shardingDao.findAllBy(PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),pageable.getSort()));


//        Page<Sharding> all = shardingDao.findAll((Specification<Sharding>) (root, query, criteriaBuilder) -> {
//            ArrayList<Predicate> predicates = new ArrayList<>();
//            predicates.add(criteriaBuilder.like(root.get("name"), "%999%"));
//
//            Predicate[] predicate = new Predicate[predicates.size()];
//
//            return criteriaBuilder.and(predicates.toArray(predicate));
//        }, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));

//        Slice<Sharding> all = criteriaNoCountDao.findAll((Specification<Sharding>) (root, query, criteriaBuilder) -> {
//            ArrayList<Predicate> predicates = new ArrayList<>();
//            predicates.add(criteriaBuilder.like(root.get("name"), "%999%"));
//
//            Predicate[] predicate = new Predicate[predicates.size()];
//
//            return criteriaBuilder.and(predicates.toArray(predicate));
//        }, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()),Sharding.class);

        Slice<Sharding> allByNameLike = shardingDao.findAllByNameLike("%999%", PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
        return allByNameLike;
    }

    @Override
    public List<Sharding> findBySql(int selId) {
        Long idLarge = Long.valueOf(selId);
        if (selId < 20) {
            idLarge = shardingDao.getTopId();
        }
        List<Sharding> allSql = shardingDao.findAllByIdBetween(idLarge-20, idLarge);
        return allSql;
    }
}
