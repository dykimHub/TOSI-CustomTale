package com.ssafy.tosi.gptconversation;

import com.ssafy.tosi.gptconversation.dto.UserInputMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;

import java.util.List;

public interface ChatgptService {
    String sendChat(UserInputMessage userInputMessage);
    String sendBye(UserInputMessage userInputMessage);
}
