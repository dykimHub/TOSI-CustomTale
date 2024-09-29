package com.tosi.customtale.dto;

import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public CustomTaleDetailDto(Long userId, String title, String customTale, String customImageS3Key, Boolean isPublic) {
        this.userId = userId;
        this.title = title;
        this.customTale = customTale;
        this.customImageS3Key = customImageS3Key;
        this.isPublic = isPublic;
    }
}
