
package com.example.data.jpa.common.base;



import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @CreatedBy
    @Column(name = "create_by", updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(name = "update_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
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
