package com.tosi.customtale.service;

import com.tosi.customtale.dto.CustomTaleRequestDto;
import com.tosi.customtale.dto.CustomTaleResponseDto;
import com.tosi.customtale.dto.TalePageResponseDto;

import java.util.List;

public interface CreateCustomTaleService {
    CustomTaleResponseDto createCustomTale(Long userId, CustomTaleRequestDto customTaleRequestDto);

    List<TalePageResponseDto> createCustomTalePagesWithDallE(CustomTaleResponseDto customTaleResponseDto);
}
