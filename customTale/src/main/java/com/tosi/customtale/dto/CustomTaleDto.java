package com.tosi.customtale.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

// CustomTaleDto 객체를 유연하게 생성하는 과정에서 null값 제거하고 캐시에 저장
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleDto {

    private Long customTaleId;
    private String title;
    private String imageS3Key;
    private String imageS3URL;
    private Boolean isPublic;

    @Builder
    public CustomTaleDto(Long customTaleId, String title, String imageS3Key, String imageS3URL, boolean isPublic) {
        this.customTaleId = customTaleId;
        this.title = title;
        this.imageS3Key = imageS3Key;
        this.imageS3URL = imageS3URL;
        this.isPublic = isPublic;
    }

    @QueryProjection
    public CustomTaleDto(Long customTaleId, String title, String imageS3Key, boolean isPublic) {
        this.customTaleId = customTaleId;
        this.title = title;
        this.imageS3Key = imageS3Key;
        this.isPublic = isPublic;
    }

    /**
     * 정적 팩토리 메서드 of를 사용하여 새로운 CustomTaleDto를 생성합니다.
     * S3 Key를 제외하여 크기를 줄입니다.
     */
    public static CustomTaleDto of(Long customTaleId, String title, String imageS3URL, boolean isPublic) {
        return CustomTaleDto.builder()
                .customTaleId(customTaleId)
                .title(title)
                .imageS3URL(imageS3URL)
                .isPublic(isPublic)
                .build();
    }

    /**
     * 기존 CustomTaleDto 객체에서 S3 Key를 제외하고 반환합니다.
     */
    public CustomTaleDto toWithoutS3Key(String imageS3URL) {
        return CustomTaleDto.builder()
                .customTaleId(this.getCustomTaleId())
                .title(this.getTitle())
                .imageS3URL(imageS3URL)
                .isPublic(this.getIsPublic())
                .build();
    }

}
