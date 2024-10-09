package com.tosi.customtale.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
