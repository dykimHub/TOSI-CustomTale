package com.tosi.customtale.service;

import com.tosi.customtale.dto.CustomResponseDto;
import com.tosi.customtale.dto.CustomTaleRequestDto;

public interface CreateCustomTaleService {
    CustomResponseDto createCustomTale(Long userId, CustomTaleRequestDto customTaleRequestDto);

}
