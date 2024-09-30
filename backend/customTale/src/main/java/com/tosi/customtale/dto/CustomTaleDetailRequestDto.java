package com.tosi.customtale.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleDetailRequestDto {

    private Long childId;
    private String title;
    private String customTale;
    private String customImageDallEURL;
    private String backGround;
    private List<String> keyWord;
    private Boolean isPublic;

}
