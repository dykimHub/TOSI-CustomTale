package com.ssafy.tosi.gptconversation;

import com.ssafy.tosi.gptconversation.dto.Message;
import com.ssafy.tosi.gptconversation.dto.UserInputMessage;
import com.ssafy.tosi.gptconversation.property.ChatgptProperties;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatRequest;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatResponse;
import io.github.flashvayne.chatgpt.exception.ChatgptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ChatgptServiceImpl implements ChatgptService {
    protected final ChatgptProperties chatgptProperties;
    private final String AUTHORIZATION;
    private final RestTemplate restTemplate = new RestTemplate();

    public ChatgptServiceImpl(ChatgptProperties chatgptProperties) {
        this.chatgptProperties = chatgptProperties;
        AUTHORIZATION = "Bearer " + chatgptProperties.getApiKey();
    }

    @Override
    public String sendChat(UserInputMessage userInputMessage) {
        List<MultiChatMessage> messages=new ArrayList<>();

        messages.add(
                new MultiChatMessage("system",
                        """
                                채팅 상대는 0~13세니까 비속어를 쓰면 안돼.
                                너는 동화속 주인공 중 한 명의 역할을 맡아서 대화하면 돼.
                                주인공에 대해서 설명하는 게 아니라
                                너가 주인공이 되어서 대화하는 거야.
                                너의 role은 assistant가 아니라 주인공이야.
                                다음은 대화할 시공간적 배경에 대한 설명이야.
                                ```
                                """
                                +"동화의 내용:"
                                + userInputMessage.getTaleScript()
                                + """
                                ```
                                다음은 너가 이입될 등장인물 이름이야.
                                """
                                +
                                "주인공 이름:"
                                + userInputMessage.getPlayName()
                                +"""
                                \n```
                                다음은 너가 대화할 상대의 이름이야.
                                """
                                +
                                "이름:"
                                + userInputMessage.getUserName()
                                + """
                                \n```
                                상대가 인사한다면 어린이 이름을 넣어서 응답해줘.
                                상대가 자신의 이름을 물어 본다면 상대의 이름을 넣어서 응답해줘.

                                등장인물의 이름이 제 3자인 것처럼 응답하면 안돼.
                                너가 바로 등장인물이야.
                                나쁜 예시: 주인공이 왜 그렇게 생각했는지 나는 몰라.
                                                                    
                                상대가 주인공의 이름을 틀리게 말하면 원래 주인공의 이름을 얘기해줘.
                                상대를 동화속 주인공으로 취급하지 마.
                                                                    
                                응답 형식은 자연스러운 문장이고 한국어만 사용하되
                                정말 한국인이 대답하는 것처럼 좀더 한국어 데이터에 최적화해줘.
                                                                    
                                응답 내용의 시공간 배경은 아까 주었던 동화의 내용이야.
                                동화 내용의 시간, 공간적 상황에 맞는 대답을 해줘.
                                                                    
                                이름:내용 형식으로 응답하는 건 하지마.
                                마치 사람 간 Direct messanger로 소통하는 것처럼 응답해.
                                마치 채팅방에서 대화하듯 소통해야 해.
                                                                    
                                상대가 일상적인 대화를 입력한다면 일상적으로 주인공의 입장에서 응답해.
                                동화에 대한 내용이 아니여도 돼.
                                                                    
                                상대에게 공감해줘.
                                조언을 단순 나열하지 말고 대화하는 흐름으로 응답해.

                                상대의 문제를 해결하기보단 대화하듯 응답하는 것에 집중해.
                             
                                너가 하려는 응답이 동화속 주인공과 대화하고 싶어하는 어린이에게
                                알맞은 응답인지 다시 한 번 생각해 보고 응답해 봐.
                                """
                +
                                """
                                ```
                                응답이 자연스러운 한국어인지 생각해봐.
                                
                                응답은 1~2문장으로 축약해서 보내.
                                응답이 1~2문장으로 축약 되었는지 생각해 봐. 응답이 너무 길면 안 돼.
                                """
                )
        );

        List<Message> conversations = userInputMessage.getUserMessages();

        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i) == null) {
                continue;
            }

            Message conversation = conversations.get(i);

            messages.add(new MultiChatMessage(conversation.getRole(), conversation.getMessage()));
        }

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

    @Override
    public String sendBye(UserInputMessage userInputMessage) {
        List<MultiChatMessage> messages=new ArrayList<>();
        messages.add(
                new MultiChatMessage("system",
                        "채팅 상대는 0~13세니까 비속어를 쓰면 안돼."
                                + """
                                너는 동화속 주인공 중 한 명의 역할을 맡아서 대화하면 돼.
                                주인공에 대해서 설명하는 게 아니라
                                너가 주인공이 되어서 대화하는 거야.
                                너의 role은 assistant가 아니라 주인공이야.
                                다음은 대화할 시공간적 배경에 대한 설명이야.
                                ```
                                동화의 내용:
                                """
                                + userInputMessage.getTaleScript()
                                + """
                                ```
                                다음은 너가 이입될 등장인물 이름이야.
                                주인공 이름:
                                """
                                + userInputMessage.getPlayName()
                                + """
                                ```
                                다음은 너가 대화할 상대의 이름이야.
                                이름:
                                """
                                + userInputMessage.getUserName()
                                + """
                                ```
                                지금은 대화를 끝내는 시점이야.
                                동화 속 주인공이 되어서 이전 대화 맥락을 분석한 응답을 보내되
                                응답 끝에는 사용자에게 잘 가라는 인사를 건네 줘.
                                인사를 건네되, 마지막 대화라서 아쉬워하는 내용을 추가해야 해. \n
                                
                                ```
                                다음은 네게 주어지는 마지막 인사 관련 정보야.
                                이걸 참조해서 응답을 보내면 돼:
                                
                                이전 대화와 관련된 대답은 1 문장과
                                인사 1문장 형식으로 응답은 총 2문장이어야해.
                                
                                응답이 자연스러운 한국어인지 생각해봐.
                                
                                응답이 2문장인지 생각해.
                                이전 대화에 대한 대답을 포함했는지 생각해.
                                다음에 다시 만나자는 인사를 응답에 포함했는지 생각해.
                                아쉬운 기색을 내비쳤는지 판단해.
                                
                                그런 다음 응답을 보내.
                                """)
        );

        List<Message> conversations = userInputMessage.getUserMessages();

        // user, gpt의 message를 누적합니다.

        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i) == null) {
                continue;
            }

            Message conversation = conversations.get(i);

            messages.add(new MultiChatMessage(conversation.getRole(), conversation.getMessage()));
        }

        MultiChatRequest multiChatRequest = new MultiChatRequest(chatgptProperties.getMulti().getModel(), messages, chatgptProperties.getMulti().getMaxTokens(), chatgptProperties.getMulti().getTemperature(), chatgptProperties.getMulti().getTopP());
        MultiChatResponse multiChatResponse = this.getResponse(this.buildHttpEntity(multiChatRequest), MultiChatResponse.class, chatgptProperties.getMulti().getUrl());
        try {
            return multiChatResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    protected <T> T getResponse(HttpEntity<?> httpEntity, Class<T> responseType, String url) {
        log.info("request url: {}, httpEntity: {}\n 49라인 로그 출력이 끝났습니다.", url, httpEntity);
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