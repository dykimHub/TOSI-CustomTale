package com.ssafy.tosi.gptconversation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GptOutputMessage {
    private Message gptMessage;
}
