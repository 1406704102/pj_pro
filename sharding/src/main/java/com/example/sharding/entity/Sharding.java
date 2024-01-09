package com.example.sharding.entity;
import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="sharding_test")
@Data
public class Sharding implements Serializable {

    @Id
    @Column(name = "s_id")
    private Long id;

    @Column(name = "s_name")
    private String name;

    @Column(name = "s_user_id")
    private Long userId;

    @Column(name = "s_status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}