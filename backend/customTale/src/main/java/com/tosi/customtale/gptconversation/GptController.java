package com.ssafy.tosi.gptconversation;

import com.ssafy.tosi.gptconversation.dto.GptOutputMessage;
import com.ssafy.tosi.gptconversation.dto.Message;
import com.ssafy.tosi.gptconversation.dto.UserInputMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gptapi")
public class GptController {
    private final ChatgptService chatgptService;

    public GptController(ChatgptService chatgptService) {
        this.chatgptService = chatgptService;
    }

    @Operation(summary="GPT에게 메세지 보내기",
            description="GPT에게 메세지를 보내고 응답을 반환 받습니다.",
            tags={"GptController"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "GPT가 응답합니다.",
                    content = @Content(schema = @Schema(implementation = GptOutputMessage.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/input")
    ResponseEntity<?> putMessage(@Parameter(description="user가 입력하는 메세지", required=true, example="세상에서 제일 맛있는 도너츠는?") @RequestBody UserInputMessage userInputMessage){
        Message responseMessage = (
                new Message("assistant",chatgptService.sendChat(userInputMessage)));

        return new ResponseEntity<>(responseMessage,
                HttpStatus.OK);
    }

    // 다음 메소드는 마지막 응답을 호출합니다.
    @PostMapping("/bye")
    ResponseEntity<?> putByeMessage(@Parameter(description="user가 입력하는 메세지", required=true, example="세상에서 제일 맛있는 도너츠는?") @RequestBody UserInputMessage userInputMessage){
        Message responseMessage = (
                new Message("assistant",chatgptService.sendBye(userInputMessage)));

        return new ResponseEntity<>(responseMessage,
                HttpStatus.OK);
    }
}
