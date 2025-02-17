package com.tosi.customtale.dto;

import lombok.*;

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
                
                Please create an illustration to be used as the cover for this fairy tale. \s
                Make it look like a scene from a Pixar/Disney animated movie with high-quality 3D rendering. \s
                Refer to the styles of the movies Tangled, Frozen. \s

                Set the background to %s. \s
                Draw a %s character in a 3D modeling style with realistic textures and lighting. \s

                🎨 Style Details: \s
                - The character should have a rounded face, large expressive eyes, and detailed, voluminous hair like Disney animation. \s
                - Skin should look smooth and soft with realistic shading and highlights. \s
                - Lighting should be cinematic and natural, with a soft glow to enhance the depth. \s
                - The overall color palette should be warm, vibrant, and lively to match the Disney/Pixar aesthetic. \s

                🚫 Do NOT include any text under any circumstances. \s
                🚫 Avoid storybook, watercolor, or flat illustration styles. \s
                🚫 Must be fully 3D animated-style, not 2D or stylized illustration. \s
                                               
                """.formatted(customTale,
                customTaleRequestDto.getBackGround(),
                customTaleRequestDto.getChildGender() == 0 ? "남자" : "여자"
        );
    }
}
