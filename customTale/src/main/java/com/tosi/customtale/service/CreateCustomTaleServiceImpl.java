package com.tosi.customtale.service;


import com.tosi.customtale.common.config.ChatGptProperties;
import com.tosi.customtale.common.config.DallEProperties;
import com.tosi.customtale.dto.CustomImageRequestDto;
import com.tosi.customtale.dto.CustomTaleRequestDto;
import com.tosi.customtale.dto.CustomTaleResponseDto;
import com.tosi.customtale.dto.TalePageResponseDto;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatRequest;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatResponse;
import io.github.flashvayne.chatgpt.dto.image.ImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
    private final ChatGptProperties chatGptProperties;
    private final DallEProperties dalleProperties;
    private final RestTemplate restTemplate;
    private final S3Service s3Service;
    @Value("${openai.api-key}")
    private String apiKey;

    /**
     * 사용자 정보, 배경, 키워드를 바탕으로 커스텀 동화 생성을 요청하고 응답에서 추출합니다.
     * 생성된 커스텀 동화를 바탕으로 OpenAI API에 이미지 생성을 요청하고 응답에서 추출합니다.
     *
     * @param userId               회원 번호
     * @param customTaleRequestDto 커스텀 동화를 만들 때 필요한 정보가 담긴 CustomTaleRequestDto 객체
     * @return 커스텀 동화, 저장된 이미지의 OpenAI API(DallE) URL을 담은 CustomTaleRequestDto 객체 반환
     */
    @Override
    public CustomTaleResponseDto createCustomTale(Long userId, CustomTaleRequestDto customTaleRequestDto) {
        // 프롬프트 생성 및 시스템 메시지 추가
        List<MultiChatMessage> chatMessages = new ArrayList<>();
        String customTalePrompt = customTaleRequestDto.getCustomTalePrompt();
        chatMessages.add(new MultiChatMessage(ROLE_SYSTEM, customTalePrompt));

        String customTale = processMultiChatRequest(chatMessages).get(1).getContent();
        String customImageURL = processImageRequest(customTale, customTaleRequestDto).getData().get(0).getUrl();

        return CustomTaleResponseDto.ofDallE(customTale, customImageURL);

    }

    /**
     * 커스텀 동화 페이지를 생성합니다.
     * 왼쪽 페이지는 삽화, 오른쪽 페이지는 동화 본문을 2문장씩 삽입합니다.
     *
     * @param customTaleResponseDto 커스텀 동화 내용과 DallE 이미지 URL이 담긴 객체
     * @return TalePageResponse 객체 리스트
     */
    @Override
    public List<TalePageResponseDto> createCustomTalePages(CustomTaleResponseDto customTaleResponseDto) {
        String[] lines = customTaleResponseDto.getCustomTale().split("\n");
        String imageS3URL = customTaleResponseDto.getCustomImageDallEURL();

        int pageNum = 1;
        List<TalePageResponseDto> talePageResponseDtoList = new ArrayList<>();

        for (int i = 0; i < lines.length; i += 2) {
            String line1 = lines[i];
            // line1이 마지막 문장이면 다음 문장은 빈 문장
            String line2 = (i + 1 < lines.length) ? lines[i + 1] : "";

            talePageResponseDtoList.add(
                    TalePageResponseDto.builder()
                            .leftNo(pageNum++)
                            .left(imageS3URL)
                            .rightNo(pageNum++)
                            .right(line1 + "\n" + line2)
                            .build()
            );
        }

        return talePageResponseDtoList;
    }

    /**
     * 커스텀 동화 생성 과정을 처리합니다.
     *
     * @param chatMessages 커스텀 동화 프롬프트를 담은 MultiChatMessage 객체 리스트
     * @return 커스텀 동화를 담은 MultiChatResponse 객체
     */
    private List<MultiChatMessage> processMultiChatRequest(List<MultiChatMessage> chatMessages) {
        MultiChatRequest multiChatRequest = makeMultiChatRequest(chatMessages);
        MultiChatResponse multiChatResponse = this.getResponse(this.buildHttpEntity(multiChatRequest), MultiChatResponse.class, chatGptProperties.getApiURL());
        chatMessages.add(new MultiChatMessage(ROLE_SYSTEM, multiChatResponse.getChoices().get(0).getMessage().getContent()));

        return chatMessages;
    }

    /**
     * 커스텀 이미지 생성 과정을 처리합니다.
     *
     * @param customTale           생성된 커스텀 동화
     * @param customTaleRequestDto 사용자 정보
     * @return 커스텀 이미지 주소를 담은 ImageResponse 객체
     */
    private ImageResponse processImageRequest(String customTale, CustomTaleRequestDto customTaleRequestDto) {
        CustomImageRequestDto customImageRequestDto = makeImageRequest(customTale, customTaleRequestDto);
        ImageResponse imageResponse = this.getResponse(this.buildHttpEntity(customImageRequestDto), ImageResponse.class, dalleProperties.getApiURL());

        return imageResponse;
    }

    /**
     * ChatGPT 설정 정보, 채팅 메시지 리스트를 담은 채팅 요청 객체를 생성합니다.
     *
     * @param chatMessages 사용자와 시스템 간의 채팅 메시지 리스트
     * @return OpenAI ChatGPT API 요청을 위한 MultiChatRequest 객체
     */
    private MultiChatRequest makeMultiChatRequest(List<MultiChatMessage> chatMessages) {
        return new MultiChatRequest(
                chatGptProperties.getModel(),
                chatMessages,
                chatGptProperties.getMaxTokens(),
                chatGptProperties.getTemperature(),
                chatGptProperties.getTopP()
        );
    }

    /**
     * DALL-E 설정 정보, 프롬프트를 담은 이미지 요청 객체를 생성합니다.
     *
     * @param customTale           생성된 커스텀 동화
     * @param customTaleRequestDto 사용자 정보
     * @return OpenAI DALL-E API 요청을 위한 CustomImageRequestDto 객체
     */
    private CustomImageRequestDto makeImageRequest(String customTale, CustomTaleRequestDto customTaleRequestDto) {
        return CustomImageRequestDto.builder()
                .model(dalleProperties.getModel())
                .size(dalleProperties.getSize())
                .quality(dalleProperties.getQuality())
                .n(dalleProperties.getN())
                .customTale(customTale)
                .customTaleRequestDto(customTaleRequestDto)
                .build();
    }

    /**
     * 요청 객체를 포함한 HttpEntity 객체를 생성합니다.
     *
     * @param request 모든 타입의 요청 객체
     * @return 생성된 HttpEntity 객체
     */
    private <T> HttpEntity<?> buildHttpEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", "Bearer " + apiKey);
        return new HttpEntity<>(request, headers);
    }

    /**
     * OpenAI API에 HTTP POST 요청을 보내고, 응답을 받아 반환합니다.
     *
     * @param httpEntity   헤더와 요청 객체를 담은 HttpEntity 객체
     * @param responseType 모든 타입의 반환 객체(여기서는 MultiChatResponse 혹은 ImageResponse)
     * @param url          요청을 보낼 API
     * @return OpneAI API 응답을 지정된 타입(T)으로 변환하여 반환
     */
    private <T> T getResponse(HttpEntity<?> httpEntity, Class<T> responseType, String url) {
        return restTemplate.postForEntity(url, httpEntity, responseType)
                .getBody();

    }


}