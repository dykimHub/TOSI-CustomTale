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
                 1. 이 동화를 대표하는 장면 하나를 그려줘. 말풍선이나 설명은 그리지마. 
                 2. 배경은 %s, 참고해.
                 3. 3D 애니메이션, 디즈니 스타일로 생동감 있게 그려줘.
                 4. %s 아이를 포함시켜줘.
                 """.formatted(customTale,
                customTaleRequestDto.getBackGround(),
                customTaleRequestDto.getChildGender() == 0 ? "남자" : "여자"
        );
    }
}
