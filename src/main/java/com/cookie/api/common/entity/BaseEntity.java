package com.cookie.api.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '생성_일시'")
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP NULL COMMENT '수정_일시'")
    protected LocalDateTime updatedAt;
    @Column(columnDefinition = "TIMESTAMP NULL COMMENT '삭제_일시'")
    protected LocalDateTime deletedAt;
    @Column(columnDefinition = "VARCHAR(1) NULL DEFAULT 'N' COMMENT '삭제_여부'")
    protected String deletedYn;
    @Column(columnDefinition = "BIGINT NULL COMMENT '삭제자'")
    protected Long deleterId;
}
