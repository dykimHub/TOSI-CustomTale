package com.tosi.customtale.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleDto {

    private Long customTaleId;
    private String title;
    private String imageS3Key;
    private Boolean isPublic;

    @QueryProjection
    public CustomTaleDto(Long customTaleId, String title, String imageS3Key, Boolean isPublic) {
        this.customTaleId = customTaleId;
        this.title = title;
        this.imageS3Key = imageS3Key;
        this.isPublic = isPublic;
    }
}
