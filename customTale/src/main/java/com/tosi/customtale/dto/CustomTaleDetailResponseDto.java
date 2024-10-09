package com.tosi.customtale.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleDetailResponseDto {

    private Long userId;
    private Long childId;
    private String title;
    private String customTale;
    private String customImageS3Key;
    private Boolean isPublic;

    @QueryProjection
    public CustomTaleDetailResponseDto(Long userId, Long childId, String title, String customTale, String customImageS3Key, Boolean isPublic) {
        this.userId = userId;
        this.childId = childId;
        this.title = title;
        this.customTale = customTale;
        this.customImageS3Key = customImageS3Key;
        this.isPublic = isPublic;
    }
}
