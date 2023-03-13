package com.cookie.api.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrappedResponse<T> {
    private String code;
    private String description;
    private T response;

    public WrappedResponse() {
        this.code = "2000";
        this.description = "응답에 성공했습니다.";
    }

    public WrappedResponse(T response) {
        this();
        this.response = response;
    }

    @Builder
    public WrappedResponse(String code, String description, T response) {
        this.code = code;
        this.description = description;
        this.response = response;
    }
}
