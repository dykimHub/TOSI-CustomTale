package com.tosi.customtale.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionCode {
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_001", "유효하지 않은 토큰입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
