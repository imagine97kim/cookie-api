package com.cookie.api.common.dto;

import com.cookie.api.ApiConstants;
import com.cookie.api.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@Slf4j
public class BaseDto {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = ApiConstants.DATE_TIME_FORMAT)
    protected LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = ApiConstants.DATE_TIME_FORMAT)
    protected LocalDateTime updatedAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = ApiConstants.DATE_TIME_FORMAT)
    protected LocalDateTime deletedAt;
    protected String deletedYn;

    public BaseDto() {}

    public void inheritBaseProperties(BaseEntity baseEntity) {
        this.createdAt = baseEntity.getCreatedAt();
        this.updatedAt = baseEntity.getUpdatedAt();
        this.deletedAt = baseEntity.getDeletedAt();
        this.deletedYn = baseEntity.getDeletedYn();
    }

    public static void fillNullByPrev (Object target, Object prev) {
        for (Field declaredField : prev.getClass().getDeclaredFields()) {
            try {
                declaredField.setAccessible(true);
                if (declaredField.get(target) == null) {
                    declaredField.set(target, declaredField.get(prev));
                }
                declaredField.setAccessible(false);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
    }
}
