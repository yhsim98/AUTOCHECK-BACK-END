package com.auto.check.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public abstract class DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @ApiModelProperty(hidden = true)
    protected Timestamp created_at;

    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    protected Timestamp modified_at;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;
}
