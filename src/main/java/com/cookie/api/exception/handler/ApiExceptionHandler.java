package com.cookie.api.exception.handler;

import com.cookie.api.ApiConstants;
import com.cookie.api.common.dto.WrappedResponse;
import com.cookie.api.exception.dto.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        log.error("Spring MVC Exception Occurred", ex);

        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ApiConstants.ApiResponseCode code = ApiConstants.ApiResponseCode.RESPONSE_INTERNAL_SEVER_ERROR;
        return new ResponseEntity<>(response(code, status.getReasonPhrase()), headers, status);
    }

    /**
     * requestParam exception
     **/
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ApiConstants.ApiResponseCode code = ApiConstants.ApiResponseCode.REQUEST_PARAMETER_INVALID;
        log.error("Exception Occurred", ex);

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(ex.getFieldError().getField())
                .append(" ")
                .append(ex.getFieldError().getDefaultMessage());

        return new ResponseEntity<>(response(code, errorMessage.toString()), headers, code.getStatus());
    }

    /**
     * 비즈니스 로직 구현에서 발생할 수 있는 runtime exception
     */
    @ExceptionHandler(BaseException.class)
    @Nullable
    public final ResponseEntity<Object> handleException(BaseException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ApiConstants.ApiResponseCode code= ex.getCode();
        HttpHeaders headers = new HttpHeaders();
        log.error("Exception Occurred", ex);

        return new ResponseEntity<>(response(code, ex.getMessage()), headers, code.getStatus());
    }
    /**
     * Servlet 관련 Exception 처리
     */
    @ExceptionHandler(ServletException.class)
    protected ResponseEntity<Object> handleNestedServletException(Exception ex, WebRequest request) {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ApiConstants.ApiResponseCode code = ApiConstants.ApiResponseCode.RESPONSE_INTERNAL_SEVER_ERROR; //Default Response Code

        if (ex instanceof ServletRequestBindingException) {
        } else if (ex instanceof HttpMediaTypeException) {
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
        } else if (ex instanceof MissingServletRequestPartException) {
        } else if (ex instanceof NoHandlerFoundException) {
        }

        HttpHeaders headers = new HttpHeaders();

        log.error("Servlet Exception", ex);
        return new ResponseEntity<>(response(code, ex.getMessage()), headers, code.getStatus());
    }
    /**
     * 그 외 예외 발생 공통 처리 부
     */
    @ExceptionHandler(Exception.class)
    @Nullable
    public final ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ApiConstants.ApiResponseCode code = ApiConstants.ApiResponseCode.RESPONSE_INTERNAL_SEVER_ERROR;
        HttpHeaders headers = new HttpHeaders();

        log.error("Unknown Internal Exception Occurred", ex);
        return new ResponseEntity<>(response(code), headers, code.getStatus());
    }

    WrappedResponse response(ApiConstants.ApiResponseCode code) {
        return response(code, "");
    }
    WrappedResponse response(ApiConstants.ApiResponseCode code, String message) {
        return WrappedResponse.builder()
                .code(code.getCode())
                .description(message)
                .build();
    }
}
