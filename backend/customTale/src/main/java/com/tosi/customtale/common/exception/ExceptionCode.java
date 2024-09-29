package com.tosi.customtale.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionCode {
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_001", "유효하지 않은 토큰입니다."),

    CUSTOM_TALE_NOT_FOUND(HttpStatus.NOT_FOUND, "CUSTOM_TALE_001", "해당 커스텀 동화를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
