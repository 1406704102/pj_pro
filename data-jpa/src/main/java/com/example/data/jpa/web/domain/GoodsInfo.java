package com.example.data.jpa.web.domain;

import com.example.data.jpa.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goods_info")
public class GoodsInfo extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "goods_name")
    private String goodsName;


    @OneToOne
    @JoinColumn(name = "type_id")
    private GoodsInfoType goodsInfoType;
}
