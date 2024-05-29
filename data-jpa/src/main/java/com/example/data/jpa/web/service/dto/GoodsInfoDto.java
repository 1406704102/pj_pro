package com.example.data.jpa.web.service.dto;


import com.example.data.jpa.web.domain.GoodsInfoType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class GoodsInfoDto {
    private Long id;

    private String goodsName;
    private GoodsInfoType goodsInfoType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoodsInfoDto goodsInfoDto = (GoodsInfoDto) o;
        return Objects.equals(id, goodsInfoDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
