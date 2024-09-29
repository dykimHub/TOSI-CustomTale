package com.tosi.customtale.service;

import com.tosi.customtale.common.exception.SuccessResponse;
import com.tosi.customtale.dto.CustomResponseDto;
import com.tosi.customtale.dto.CustomTaleDetailDto;
import com.tosi.customtale.dto.CustomTaleDto;
import com.tosi.customtale.dto.TalePageResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomTaleService {

    List<CustomTaleDto> findCustomTaleList(Long userId, Pageable pageable);

    SuccessResponse addCustomTale(Long userId, CustomTaleDetailDto customTaleDetailDto);

    List<TalePageResponseDto> createCustomTalePages(CustomResponseDto customResponseDto);

    Long findUserAuthorization(String accessToken);

}
