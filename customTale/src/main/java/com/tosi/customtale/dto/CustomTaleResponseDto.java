package com.tosi.customtale.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleResponseDto {
    private String customTale;
    private String customImageDallEURL;

    @Builder
    public CustomTaleResponseDto(String customTale, String customImageDallEURL) {
        this.customTale = customTale;
        this.customImageDallEURL = customImageDallEURL;
    }

    public static CustomTaleResponseDto of(String customTale, String customImageDallEURL) {
        return CustomTaleResponseDto.builder()
                .customTale(customTale)
                .customImageDallEURL(customImageDallEURL)
                .build();
    }
}
