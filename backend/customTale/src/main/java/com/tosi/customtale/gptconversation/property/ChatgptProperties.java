package com.ssafy.tosi.gptconversation.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "chatgpt")
public class ChatgptProperties {

    private String apiKey;

    private String url = "https://api.openai.com/v1/chat/completions";

    private String model = "gpt-3.5-turbo-1106";

    private Integer maxTokens = 100;

    private Double temperature = 1.0;

    private Double topP = 1.0;

    private MultiChatProperties multi;

    public ChatgptProperties() {
        this.multi = new MultiChatProperties();
    }

    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setTopP(Double topP) {
        this.topP = topP;
    }

    public void setMulti(MultiChatProperties multi) {
        this.multi = multi;
    }
}