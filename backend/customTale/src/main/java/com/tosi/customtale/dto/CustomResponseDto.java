package com.tosi.customtale.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomResponseDto {
    private String customTale;
    private String customImageURL;

    @Builder
    public CustomResponseDto(String customTale, String customImageURL) {
        this.customTale = customTale;
        this.customImageURL = customImageURL;
    }
}
