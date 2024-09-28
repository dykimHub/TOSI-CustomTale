package com.tosi.customtale.service;

import com.tosi.customtale.dto.CustomTaleRequestDto;

public interface CreateCustomTaleService {
    String createCustomTale(String accessToken, CustomTaleRequestDto customTaleRequestDto);

}
