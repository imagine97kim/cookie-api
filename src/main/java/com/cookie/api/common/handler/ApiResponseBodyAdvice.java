package com.cookie.api.common.handler;

import com.cookie.api.common.dto.WrappedResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;

@Slf4j
@ControllerAdvice
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        WrappedResponse res = null;
        if (body instanceof WrappedResponse) {
            res = (WrappedResponse) body;
        } else if (body == null) {
            res = new WrappedResponse(new HashMap<String, Object>());
        } else if (body instanceof byte[] || selectedConverterType.equals(StringHttpMessageConverter.class)){
            return body;
        } else {
           res = new WrappedResponse(body);
        }
        log.info("RESPONSE-BODY: ", ReflectionToStringBuilder.toString(res, ToStringStyle.JSON_STYLE));
        return res;
    }
}
