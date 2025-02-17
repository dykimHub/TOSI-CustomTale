package com.tosi.customtale.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.tosi.customtale.entity.CustomTale;
import lombok.*;

@With
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleDetailResponseDto {

    private Long userId;
    private Long childId;
    private String title;
    private String customTale;
    private String customImageS3Key;
    private String customImageS3URL;
    private Boolean isPublic;

    @Builder
    public CustomTaleDetailResponseDto(Long userId, Long childId, String title, String customTale, String customImageS3Key, String customImageS3URL, Boolean isPublic) {
        this.userId = userId;
        this.childId = childId;
        this.title = title;
        this.customTale = customTale;
        this.customImageS3Key = customImageS3Key;
        this.customImageS3URL = customImageS3URL;
        this.isPublic = isPublic;

    }

    @QueryProjection
    public CustomTaleDetailResponseDto(Long userId, Long childId, String title, String customTale, String customImageS3Key, Boolean isPublic) {
        this.userId = userId;
        this.childId = childId;
        this.title = title;
        this.customTale = customTale;
        this.customImageS3Key = customImageS3Key;
        this.isPublic = isPublic;
    }

    public static CustomTaleDetailResponseDto of(CustomTale customTale, String customImageS3URL) {
        return CustomTaleDetailResponseDto.builder()
                .userId(customTale.getUserId())
                .childId(customTale.getChildId())
                .title(customTale.getTitle())
                .customTale(customTale.getContent())
                .customImageS3URL(customImageS3URL)
                .isPublic(customTale.getIsPublic())
                .build();

    }

    public CustomTaleDetailResponseDto toWithoutS3Key(String s3URL) {
        return CustomTaleDetailResponseDto.builder()
                .userId(this.userId)
                .childId(this.childId)
                .title(this.title)
                .customTale(this.customTale)
                .customImageS3URL(s3URL)
                .isPublic(this.isPublic)
                .build();
    }
}
