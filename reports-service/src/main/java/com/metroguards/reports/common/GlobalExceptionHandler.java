package com.metroguards.reports.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Pass through 403/404/etc. coming from backend-service. */
    @ExceptionHandler(RestClientResponseException.class)
    ResponseEntity<Map<String, String>> downstream(RestClientResponseException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(Map.of("error", "Backend responded: " + e.getStatusText()));
    }
}
