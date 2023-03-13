package com.cookie.api.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrappedRequest<T> {
    private T request;
    public WrappedRequest() { }
    @Builder
    public WrappedRequest(T request) { this.request = request; }
}
