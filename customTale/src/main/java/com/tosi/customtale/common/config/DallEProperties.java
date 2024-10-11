package com.tosi.customtale.common.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component // 빈 등록
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DallEProperties {
    private final String apiURL = "https://api.openai.com/v1/images/generations";
    private final String model = "dall-e-3";
    private final String size = "1024x1024"; // 이미지 크기
    private final String quality = "standard"; // 이미지 품질
    private final int n = 1; // 이미지 수
    private String prompt; // 이미지 생성 프롬프트

}
