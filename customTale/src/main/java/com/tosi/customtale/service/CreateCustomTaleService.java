package com.tosi.customtale.service;

import com.tosi.common.dto.TalePageDto;
import com.tosi.customtale.dto.CustomTaleRequestDto;
import com.tosi.customtale.dto.CustomTaleResponseDto;

import java.util.List;

public interface CreateCustomTaleService {
    CustomTaleResponseDto createCustomTale(Long userId, CustomTaleRequestDto customTaleRequestDto);

}
