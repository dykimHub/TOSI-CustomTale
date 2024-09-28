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

    public String getCustomTalePrompt() {
        return """
                1. 주어진 배경과 키워드를 활용해서 어린이를 위한 판타지 동화를 상상해줘.
                2. 주인공 이름: %s.  
                3. 자연스러운 한국어를 사용해줘.
                4. 동화에 맞게 상냥하고 친근한 존댓말로 이야기해줘.
                5. 10줄 내외로 만들어주고, 문장마다 줄바꿈 기호(\n)를 삽입해줘. 문단은 나누지 마.
                6. 배경: %s
                7. 키워드: %s. 키워드에 비속어가 있으면 포함하지 말아줘.
                """.formatted(this.childName,
                this.backGround,
                String.join(", ", this.keyWord)
        );
    }
}
