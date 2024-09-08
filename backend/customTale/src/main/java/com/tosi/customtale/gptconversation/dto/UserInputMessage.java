package com.ssafy.tosi.gptconversation.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInputMessage {
    private List<Message> userMessages;
    private String taleScript;
    private String playName;
    private String userName;
}