package com.tosi.customtale.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomTaleDto {

    private int userId;
    private String title;
    private CustomResponseDto customResponseDto;
    private boolean isPublic;

}
