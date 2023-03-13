package com.cookie.api.exception.dto;

import com.cookie.api.ApiConstants;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private ApiConstants.ApiResponseCode code;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(ApiConstants.ApiResponseCode code) {
        super(code.getReason());
        this.code = code;
    }

    public BaseException(ApiConstants.ApiResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(ApiConstants.ApiResponseCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BaseException(ApiConstants.ApiResponseCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
