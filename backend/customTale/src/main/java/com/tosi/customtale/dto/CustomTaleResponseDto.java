package com.tosi.customtale.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleResponseDto {
    private String customTale;
    private String customImageS3Key;

    @Builder
    public CustomTaleResponseDto(String customTale, String customImageS3Key) {
        this.customTale = customTale;
        this.customImageS3Key = customImageS3Key;
    }

    public static CustomTaleResponseDto of(String customTale, String customImageS3Key) {
        return CustomTaleResponseDto.builder()
                .customTale(customTale)
                .customImageS3Key(customImageS3Key)
                .build();
    }
}
