package com.tosi.customtale.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleResponseDto {
    private String customTale;
    private String customImageDallEURL;
    private String customImageS3Key;

    @Builder
    public CustomTaleResponseDto(String customTale, String customImageDallEURL, String customImageS3Key) {
        this.customTale = customTale;
        this.customImageDallEURL = customImageDallEURL;
        this.customImageS3Key = customImageS3Key;
    }

    public static CustomTaleResponseDto ofDallE(String customTale, String customImageDallEURL) {
        return CustomTaleResponseDto.builder()
                .customTale(customTale)
                .customImageDallEURL(customImageDallEURL)
                .build();
    }

    public static CustomTaleResponseDto ofS3(String customTale, String customImageS3Key) {
        return CustomTaleResponseDto.builder()
                .customTale(customTale)
                .customImageS3Key(customImageS3Key)
                .build();
    }
}
