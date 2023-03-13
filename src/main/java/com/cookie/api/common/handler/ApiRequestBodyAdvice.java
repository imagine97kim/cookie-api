package com.cookie.api.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.cookie.api.common.dto.WrappedRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice
public class ApiRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Autowired
    private MappingJackson2HttpMessageConverter converter;
    @Autowired
    private ObjectMapper objectMapper;
    @PostConstruct
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        if (!parameter.getParameterType().equals(WrappedRequest.class)) {
            WrappedRequest wrappedRequest = null;
            Type wrappedRequestType = ParameterizedTypeImpl.make(WrappedRequest.class, new Type[]{targetType}, null);
            wrappedRequest = (WrappedRequest) converter.read(wrappedRequestType, null, inputMessage);
            return inputMessage(objectMapper.writeValueAsBytes(wrappedRequest.getRequest()), inputMessage.getHeaders());
        }

        return inputMessage;
    }

    private HttpInputMessage inputMessage(byte[] body, HttpHeaders headers) {

        log.info("[Request Info] Header : " + ReflectionToStringBuilder.toString(headers, ToStringStyle.SIMPLE_STYLE));
        log.info("[Request Info] Body : " + new String(body));

        // for audit logging
        MDC.put("REQ-BODY", new String(body));

        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(body);
            }

            @Override
            public HttpHeaders getHeaders() {
                return headers;
            }
        };
    }
}
