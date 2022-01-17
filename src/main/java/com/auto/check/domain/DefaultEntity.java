package com.auto.check.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class DefaultEntity {

    @CreatedDate
    @ApiModelProperty(hidden = true)
    protected Timestamp created_at;

    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    protected Timestamp modified_at;
}
