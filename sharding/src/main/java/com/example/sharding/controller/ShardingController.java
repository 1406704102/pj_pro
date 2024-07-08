package com.example.sharding.controller;

import com.example.sharding.entity.Sharding;
import com.example.sharding.entity.ShardingBase;
import com.example.sharding.service.ShardingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
        Slice<Sharding> query = service.query(pageable);
        return new ResponseEntity<>(query, HttpStatus.OK);
    }
    @GetMapping("/querySql")
    public ResponseEntity<Object> querySql(int id) {
        List<Sharding> query = service.findBySql(id);
        return new ResponseEntity<>(query, HttpStatus.OK);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://139.155.84.163:3306/sharding_sphere_demo1?" +
                "serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&" +
                "rewriteBatchedStatements=true";
        String username = "root";
        String password = "root_pangjie";

        //定义连接、statement对象
        Connection conn = null;
        PreparedStatement pstm0 = null;
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;

        //加载jdbc驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //连接mysql
        conn = DriverManager.getConnection(url, username, password);
        //将自动提交关闭
        conn.setAutoCommit(false);

        //编写sql
        String sql0 = "insert into sharding_test_0 (s_id,s_name,s_user_id,s_status) values (?,?,?,?)";
        String sql1 = "insert into sharding_test_1 (s_id,s_name,s_user_id,s_status) values (?,?,?,?)";
        String sql2 = "insert into sharding_test_2 (s_id,s_name,s_user_id,s_status) values (?,?,?,?)";
        //预编译sql
        pstm0 = conn.prepareStatement(sql0);
        pstm1 = conn.prepareStatement(sql1);
        pstm2 = conn.prepareStatement(sql2);

        long bTime1 = System.currentTimeMillis();
        for (int ii = 0; ii < 30000000; ii++) {
            if (ii % 3 == 0) {
                pstm0.setInt(1, ii);
                pstm0.setString(2, ii + "name");
                pstm0.setInt(3, ii);
                pstm0.setString(4, ii+"status");
                pstm0.addBatch();
            } else if (ii % 3 == 1) {
                pstm1.setInt(1, ii);
                pstm1.setString(2, ii + "name");
                pstm1.setInt(3, ii);
                pstm1.setString(4, ii+"status");
                pstm1.addBatch();
            }else {
                pstm2.setInt(1, ii);
                pstm2.setString(2, ii + "name");
                pstm2.setInt(3, ii);
                pstm2.setString(4, ii+"status");
                pstm2.addBatch();
            }
            if (ii % 100000 == 0) {

                pstm0.executeBatch();
                pstm1.executeBatch();
                pstm2.executeBatch();

                conn.commit();
                long eTime = System.currentTimeMillis();
                System.out.println("成功插入"+ ii+"条数据耗时：" + (eTime - bTime1)/1000);
            }
        }
    }


}
