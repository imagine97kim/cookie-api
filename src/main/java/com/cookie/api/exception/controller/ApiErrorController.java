package com.cookie.api.exception.controller;

import com.cookie.api.ApiConstants;
import com.cookie.api.exception.dto.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/error")
public class ApiErrorController implements ErrorController {

    @GetMapping
    public void error(HttpServletRequest req) {
        HttpStatus status = getStatus(req);
        log.error("" + getRequestUrl(req));
        if (status == HttpStatus.FORBIDDEN) {

        }
        throw new BadRequestException(ApiConstants.ApiResponseCode.RESPONSE_INTERNAL_SEVER_ERROR);
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    protected String getRequestUrl(HttpServletRequest request) {
        try {
            return (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        } catch (Exception ex) {
            return "";
        }
    }
}