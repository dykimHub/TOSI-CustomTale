package com.tosi.customtale.service;


import com.tosi.customtale.common.config.OpenAIProperties;
import com.tosi.customtale.dto.CustomTaleRequestDto;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatRequest;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class CreateCustomTaleServiceImpl implements CreateCustomTaleService {
    private static final String ROLE_SYSTEM = "system";
    private final OpenAIProperties openAIProperties;
    private final RestTemplate restTemplate;
    @Value("${service.user.url}")
    private String userURL;

    /**
     * 사용자 정보, 배경, 키워드를 바탕으로 OpenAI API에 커스텀 동화 생성을 요청합니다.
     *
     * @param accessToken 로그인한 사용자의 토큰
     * @param customTaleRequestDto 커스텀 동화를 만들 때 필요한 정보가 담긴 CustomTaleRequestDto 객체
     * @return OpenAI API의 응답만 추출한 문자열
     */
    @Override
    public String createCustomTale(String accessToken, CustomTaleRequestDto customTaleRequestDto) {
        // 사용자 인증
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        Long userId = restTemplate.exchange(userURL + "auth",
                HttpMethod.GET, httpEntity, Long.class).getBody();

        // 프롬프트 생성 및 시스템 메시지 추가
        List<MultiChatMessage> multiChatMessageList = new ArrayList<>();
        String customPrompt = customTaleRequestDto.getCustomPrompt();
        multiChatMessageList.add(new MultiChatMessage(ROLE_SYSTEM, customPrompt));

        return processChatRequest(multiChatMessageList).get(1).getContent();

    }

    /**
     * MultiChatRequest 객체를 생성하고 이를 감싼 HttpEntity 객체로 OpenAI API에 요청을 보냅니다.
     * 응답받은 시스템 메시지를 기존 채팅 메시지 리스트에 추가한 후 업데이트된 리스트를 반환합니다.
     *
     * @param multiChatMessageList 사용자와 시스템 간의 기존 채팅 메시지 리스트
     * @return OpenAI API 응답을 반영한 업데이트된 채팅 메시지 리스트
     */
    private List<MultiChatMessage> processChatRequest(List<MultiChatMessage> multiChatMessageList) {
        // MultiChatRequest 객체 생성
        MultiChatRequest multiChatRequest = makeMultiChatRequest(multiChatMessageList);

        // OpenAI API에 요청 보내고 받은 응답을 추가
        MultiChatResponse multiChatResponse = restTemplate.postForEntity(
                        openAIProperties.getApiURL(),
                        buildHttpEntity(multiChatRequest),
                        MultiChatResponse.class)
                .getBody();
        multiChatMessageList.add(new MultiChatMessage(ROLE_SYSTEM, multiChatResponse.getChoices().get(0).getMessage().getContent()));

        return multiChatMessageList;
    }


    /**
     * OpenAI API 설정 정보, 채팅 메시지 리스트를 담은 MultiChatRequest 객체를 생성합니다.
     *
     * @param multiChatMessageList 사용자와 시스템 간의 채팅 메시지 리스트
     * @return OpenAI API 요청을 위한 MultiChatRequest 객체
     */
    private MultiChatRequest makeMultiChatRequest(List<MultiChatMessage> multiChatMessageList) {
        return new MultiChatRequest(
                openAIProperties.getModel(),
                multiChatMessageList,
                openAIProperties.getMaxTokens(),
                openAIProperties.getTemperature(),
                openAIProperties.getTopP()
        );
    }

    /**
     * MultiChatRequest 객체와 헤더를 포함한 HttpEntity 객체를 생성합니다.
     *
     * @param multiChatRequest OpenAI API 설정 정보, 채팅 메시지 리스트를 담은 MultiChatRequest 객체
     * @return OpenAI API에 보낼 HttpEntity 객체
     */
    private HttpEntity<?> buildHttpEntity(MultiChatRequest multiChatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", "Bearer " + openAIProperties.getApiKey());
        return new HttpEntity<>(multiChatRequest, headers);
    }


}