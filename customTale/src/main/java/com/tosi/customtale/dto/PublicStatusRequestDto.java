package com.tosi.customtale.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublicStatusRequestDto {
    private Long customTaleId;
    private Boolean isPublic;
}
