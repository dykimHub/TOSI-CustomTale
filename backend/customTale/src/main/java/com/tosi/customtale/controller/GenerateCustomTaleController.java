package com.tosi.customtale.controller;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class GenerateCustomTaleController {
//    private final GenerateCustomTaleService generateCustomTale;
//
//    public GenerateCustomTaleController(GenerateCustomTaleService generateCustomTale) {
//        this.generateCustomTale = generateCustomTale;
//    }
//
//    @Operation(summary="GPT에게 동화 생성 요청하기",
//            description="GPT가 동화를 생성합니다.",
//            tags={"GptController"}
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "GPT가 응답합니다.",
//                    content = @Content(schema = @Schema(implementation = GptOutputMessage.class))),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
//    })
//    @PostMapping("/customtale/input")
//    ResponseEntity<?> putMessage(@Parameter(description="user가 입력하는 메세지", required=true, example="세상에서 제일 맛있는 도너츠는?") @RequestBody UserInputMessage userInputMessage){
//        System.out.println("입력된 메세지: "+userInputMessage);
//        List<MultiChatMessage> messages = Arrays.asList(
//                new MultiChatMessage("system","이야기를 들을 상대는 0~13세 아이야. 비속어를 쓰지 않도록 해.자연스러운 문맥의 동화를 만들어야해."),
//                new MultiChatMessage("user", userInputMessage.getUserMessage()));
//
//
//        GptOutputMessage responseMessage = new GptOutputMessage(generateCustomTale.sendChat(messages));
//
//        return new ResponseEntity<>(responseMessage,
//                HttpStatus.OK);
//    }
}
