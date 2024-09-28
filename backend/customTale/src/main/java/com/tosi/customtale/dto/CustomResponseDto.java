package com.tosi.customtale.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomResponseDto {
    private String customTale;
    private String customImageS3Key;

    @Builder
    public CustomResponseDto(String customTale, String customImageS3Key) {
        this.customTale = customTale;
        this.customImageS3Key = customImageS3Key;
    }
}
