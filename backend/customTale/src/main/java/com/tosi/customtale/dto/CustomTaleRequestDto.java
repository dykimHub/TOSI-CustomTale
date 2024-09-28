package com.tosi.customtale.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleRequestDto {
    private String childName;
    private int childGender;
    private String backGround;
    private List<String> keyWord;

    public String getCustomPrompt() {
        return """
                1. 주어진 배경과 키워드를 활용해서 어린이를 위한 판타지 동화를 상상해줘.
                2. 자연스러운 한국어를 사용해줘.
                3. 동화에 맞게 상냥하고 친근한 존댓말로 이야기해줘.
                4. 10줄 내외로 만들어주고, 문장마다 줄바꿈 기호(\n)를 삽입해줘. 문단은 나누지 마.
                5. 주인공 이름: %s.  
                6. 성별: %s. 성별을 고려하되, 동화 내에서 직접적으로 언급하지마.
                7. 배경: %s
                8. 키워드: %s
                """
                .formatted(this.childName,
                        this.childGender == 0 ? "남자" : "여자",
                        this.backGround,
                        String.join(", ", this.keyWord)
                );
    }
}
