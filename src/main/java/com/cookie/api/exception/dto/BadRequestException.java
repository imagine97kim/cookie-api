package com.cookie.api.exception.dto;


import com.cookie.api.ApiConstants;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(ApiConstants.ApiResponseCode code) {
        super(code);
    }

    public BadRequestException(ApiConstants.ApiResponseCode code, String message) {
        super(code, message);
    }

    public BadRequestException(ApiConstants.ApiResponseCode code, Throwable cause) {
        super(code, cause);
    }

    public BadRequestException(ApiConstants.ApiResponseCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
