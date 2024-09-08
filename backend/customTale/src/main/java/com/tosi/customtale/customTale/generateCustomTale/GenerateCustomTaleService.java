package com.ssafy.tosi.customTale.generateCustomTale;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;

import java.util.List;

public interface GenerateCustomTaleService {
    String sendChat(List<MultiChatMessage> messages);
}
