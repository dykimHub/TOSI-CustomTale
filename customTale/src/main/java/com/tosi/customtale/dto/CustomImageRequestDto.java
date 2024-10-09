package com.tosi.customtale.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomImageRequestDto {
    // OpenAI에서 사용하는 변수명과 동일해야 함
    private String model;
    private String size;
    private String quality;
    private int n;

    private String prompt;

    @Builder
    public CustomImageRequestDto(String model, String size, String quality, int n, String customTale, CustomTaleRequestDto customTaleRequestDto) {
        this.model = model;
        this.size = size;
        this.quality = quality;
        this.n = n;
        this.prompt = """
                %s
                1. 이 동화의 표지로 쓸 그림을 그려줘.
                2. 디즈니 스타일로 3D로 생동감 있게 그려줘.
                3. 배경은 %s, 참고해.
                4. %s 아이를 포함시켜줘.
                """.formatted(customTale,
                customTaleRequestDto.getBackGround(),
                customTaleRequestDto.getChildGender() == 0 ? "남자" : "여자"
        );
    }
}
