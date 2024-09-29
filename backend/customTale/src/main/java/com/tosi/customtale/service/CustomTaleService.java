package com.tosi.customtale.service;

import com.tosi.customtale.common.exception.SuccessResponse;
import com.tosi.customtale.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomTaleService {

    List<CustomTaleDto> findCustomTaleList(Long userId, Pageable pageable);

    List<CustomTaleDto> findPublicCustomTaleList(Pageable pageable);

    SuccessResponse addCustomTale(Long userId, CustomTaleDetailDto customTaleDetailDto);

    List<TalePageResponseDto> findCustomTaleDetail(Long userId, Long customTaleId);

    SuccessResponse modifyCustomTalePublicStatus(PublicStatusRequestDto publicStatusRequestDto);

    SuccessResponse deleteCustomTale(Long customTaleId);

    List<TalePageResponseDto> createCustomTalePages(CustomTaleResponseDto customTaleResponseDto);

    Long findUserAuthorization(String accessToken);

}
