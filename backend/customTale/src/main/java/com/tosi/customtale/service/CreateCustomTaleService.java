package com.tosi.customtale.service;

import com.tosi.customtale.dto.CustomTaleResponseDto;
import com.tosi.customtale.dto.CustomTaleRequestDto;

public interface CreateCustomTaleService {
    CustomTaleResponseDto createCustomTale(Long userId, CustomTaleRequestDto customTaleRequestDto);

}
