package com.cookie.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

public class ApiConstants {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final int JWT_TOKEN_INVALID = 0;
    public static final int JWT_TOKEN_ACCESS_VALID = 1;
    public static final int JWT_TOKEN_REFRESH_VALID = 2;
    public static final int JWT_TOKEN_VALID = 3;

    @Getter
    public enum ApiResponseCode {

        REQUEST_PARAMETER_MISSING("4040", "파라미터가 없습니다.", HttpStatus.BAD_REQUEST),
        REQUEST_PARAMETER_INVALID("4041", "파라미터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
        RESPONSE_SUCCESS("2000", "성공입니다.", HttpStatus.OK),
        RESPONSE_UNAUTHORIZED("4010", "권한이 없습니다.", HttpStatus.UNAUTHORIZED),
        RESPONSE_TOKEN_EXPIRED("4010", "토큰의 유효기간이 아닙니다.", HttpStatus.UNAUTHORIZED),
        RESPONSE_TOKEN_INVALID("4010", "유효한 토큰이 아닙니다.", HttpStatus.UNAUTHORIZED),
        RESPONSE_NOT_FOUND("4042", "대상을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
        RESPONSE_INTERNAL_SEVER_ERROR("5020", "서버 내부 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

        private final String code;
        private final String reason;
        private final HttpStatus status;

        ApiResponseCode(String code, String reason, HttpStatus status) {
            this.code = code;
            this.reason = reason;
            this.status = status;
        }
    }
}
