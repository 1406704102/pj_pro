package com.example.data.jpa.common.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class BaseDTO  implements Serializable {

    private String createBy;

    private String updatedBy;

    private Timestamp createTime;

    private Timestamp updateTime;

//    @Override
//    public String toString() {
//        ToStringBuilder builder = new ToStringBuilder(this);
//        Field[] fields = this.getClass().getDeclaredFields();
//        try {
//            for (Field f : fields) {
//                f.setAccessible(true);
//                builder.append(f.getName(), f.get(this)).append("\n");
//            }
//        } catch (Exception e) {
//            builder.append("toString builder encounter an error");
//        }
//        return builder.toString();
//    }
}
