package com.tosi.customtale.service;

import com.tosi.customtale.dto.CustomResponseDto;
import com.tosi.customtale.dto.TalePageResponseDto;

import java.util.List;

public interface CustomTaleService {
    Long findUserAuthorization(String accessToken);

    List<TalePageResponseDto> createCustomTalePages(CustomResponseDto customResponseDto);
}
