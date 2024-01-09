package com.example.sharding.controller;

import com.example.sharding.entity.Sharding;
import com.example.sharding.service.ShardingService;
import lombok.AllArgsConstructor;
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
}
