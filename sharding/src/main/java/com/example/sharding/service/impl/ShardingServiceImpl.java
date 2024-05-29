package com.example.sharding.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.sharding.entity.Sharding;
import com.example.sharding.entity.ShardingBase;
import com.example.sharding.repository.ShardingBaseDao;
import com.example.sharding.repository.ShardingDao;
import com.example.sharding.service.ShardingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.LongStream;

@Service
@AllArgsConstructor
public class ShardingServiceImpl implements ShardingService {

    private final ShardingDao shardingDao;
    private final ShardingBaseDao shardingBaseDao;
    @Override
    public void save() {
        LongStream.range(1, 31).forEach(f -> {
            Sharding sharding = new Sharding();
            long id = IdUtil.getSnowflake().nextId();
            sharding.setId(f);
            sharding.setName("java");
            sharding.setUserId(id);
            sharding.setStatus("Normal");
            shardingDao.save(sharding);
            System.out.println(id % 3);
            System.out.println(id % 2 + 1 + "->" + id);
        });

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
    public Page<Sharding> query(Pageable pageable) {
        Page<Sharding> all = shardingDao.findAll(pageable);
        return all;
    }
}
