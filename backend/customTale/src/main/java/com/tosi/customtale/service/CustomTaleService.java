package com.tosi.customtale.service;

import com.tosi.customtale.common.exception.SuccessResponse;
import com.tosi.customtale.dto.CustomResponseDto;
import com.tosi.customtale.dto.CustomTaleDto;
import com.tosi.customtale.dto.TalePageResponseDto;

import java.util.List;

public interface CustomTaleService {
    Long findUserAuthorization(String accessToken);

    List<TalePageResponseDto> createCustomTalePages(CustomResponseDto customResponseDto);

    SuccessResponse addCustomTale(Long userId, CustomTaleDto customTaleDto);
}
