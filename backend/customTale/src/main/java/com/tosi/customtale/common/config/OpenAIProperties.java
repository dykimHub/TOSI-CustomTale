package com.tosi.customtale.common.config;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // 빈 등록
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAIProperties {
    @Value("${openai.api-key}")
    private String apiKey;
    @Value("${openai.url}")
    private String apiURL;
    @Value("${openai.model}")
    private String model; // OpenAI 모델 버전
    @Value("${openai.maxTokens}")
    private Integer maxTokens; // 한 번의 요청에서 입력과 출력에 사용할 최대 토큰 수
    @Value("${openai.temperature}")
    private Double temperature; // 랜덤성 조절; 1에 가까울수록 창의적인 답변, 0에 가까울수록 일관적인 답변
    @Value("${openai.topP}")
    private Double topP; // 확률 분포 조절; 1에 가까울수록 다양한 토큰, 0에 가까울수록 정답일 확률이 높은 토큰만 선택


}

