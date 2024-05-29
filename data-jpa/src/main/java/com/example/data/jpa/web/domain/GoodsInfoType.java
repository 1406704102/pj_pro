package com.example.data.jpa.web.domain;

import com.example.data.jpa.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.loader.plan.spi.EntityFetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goods_info_type")
public class GoodsInfoType extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "goods_type_name")
    private String goodsTypeName;


//    @OneToMany(mappedBy = "goodsInfoType")
//    private List<GoodsInfo> goodsInfos;
}
