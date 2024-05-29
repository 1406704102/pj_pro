package com.example.sharding.controller;

import com.example.sharding.entity.Sharding;
import com.example.sharding.entity.ShardingBase;
import com.example.sharding.service.ShardingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sharding")
@AllArgsConstructor
public class ShardingController {

    private final ShardingService service;

    @PostMapping
    public void save() {
        service.save();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") Long id) {
        Sharding byId = service.findById(id);
        System.out.println(byId);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }
    @GetMapping("/base/{id}")
    public ResponseEntity<Object> getBase(@PathVariable("id") Long id) {
        ShardingBase byId = service.findBaseById(id);
        System.out.println(byId);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<Object> query(Pageable pageable) {
        Page<Sharding> query = service.query(pageable);
        return new ResponseEntity<>(query, HttpStatus.OK);
    }
}
