package com.tosi.customtale.common.config;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component // 빈 등록
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatGptProperties {
    private final String apiURL = "https://api.openai.com/v1/chat/completions";
    private final String model = "gpt-4o-mini"; // OpenAI 모델 버전한 번의 요청에서 입력과 출력에 사용할 최대 토큰 수
    private final int maxTokens = 1000;
    private final Double temperature = 1.0; // 랜덤성 조절; 1에 가까울수록 창의적인 답변, 0에 가까울수록 일관적인 답변
    private final Double topP = 1.0; // 확률 분포 조절; 1에 가까울수록 다양한 토큰, 0에 가까울수록 정답일 확률이 높은 토큰만 선택


}

