package com.tosi.customtale.service;

import com.tosi.customtale.dto.CustomTaleRequestDto;
import com.tosi.customtale.dto.CustomTaleResponseDto;

public interface CreateCustomTaleService {
    CustomTaleResponseDto createCustomTale(Long userId, CustomTaleRequestDto customTaleRequestDto);

}
