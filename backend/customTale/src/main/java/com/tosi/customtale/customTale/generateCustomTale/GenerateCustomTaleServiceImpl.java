package com.ssafy.tosi.customTale.generateCustomTale;

import com.ssafy.tosi.customTale.generateCustomTale.property.CustomTalegptProperties;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatRequest;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatResponse;
import io.github.flashvayne.chatgpt.exception.ChatgptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Slf4j
@Service
public class GenerateCustomTaleServiceImpl implements GenerateCustomTaleService {
    protected final CustomTalegptProperties chatgptProperties;
    private final String AUTHORIZATION;
    private final RestTemplate restTemplate = new RestTemplate();

    public GenerateCustomTaleServiceImpl(CustomTalegptProperties chatgptProperties) {
        this.chatgptProperties = chatgptProperties;
        AUTHORIZATION = "Bearer " + chatgptProperties.getApiKey();
    }

    @Override
    public String sendChat(List<MultiChatMessage> messages) {
        MultiChatRequest multiChatRequest = new MultiChatRequest(chatgptProperties.getMulti().getModel(), messages, chatgptProperties.getMulti().getMaxTokens(), chatgptProperties.getMulti().getTemperature(), chatgptProperties.getMulti().getTopP());
        MultiChatResponse multiChatResponse = this.getResponse(this.buildHttpEntity(multiChatRequest), MultiChatResponse.class, chatgptProperties.getMulti().getUrl());
        try {
            return multiChatResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    protected <T> HttpEntity<?> buildHttpEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", AUTHORIZATION);

        return new HttpEntity<>(request, headers);
    }

    protected <T> T getResponse(HttpEntity<?> httpEntity, Class<T> responseType, String url) {
        log.info("request url: {}, httpEntity: {}", url, httpEntity);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, httpEntity, responseType);
        if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value()) {
            log.error("error response status: {}", responseEntity);

            throw new ChatgptException("error response status :" + responseEntity.getStatusCodeValue());
        } else {
            log.info("response: {}", responseEntity);
        }
        return responseEntity.getBody();
    }

}