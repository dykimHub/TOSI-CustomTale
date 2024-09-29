package com.tosi.customtale.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleDetailDto {

    private Long userId;
    private String title;
    private String customTale;
    private String customImageS3Key;
    private Boolean isPublic;

}
