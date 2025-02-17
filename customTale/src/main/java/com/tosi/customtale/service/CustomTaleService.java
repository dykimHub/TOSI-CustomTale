package com.tosi.customtale.service;

import com.tosi.common.dto.TalePageDto;
import com.tosi.common.exception.SuccessResponse;
import com.tosi.customtale.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomTaleService {

    List<CustomTaleDto> findCustomTaleList(Long userId, Pageable pageable);

    List<CustomTaleDto> findPublicCustomTaleList(Pageable pageable);

    SuccessResponse addCustomTale(Long userId, CustomTaleDetailRequestDto customTaleDetailRequestDto);

    List<TalePageDto> findCustomTaleDetail(Long userId, Long customTaleId);

    SuccessResponse modifyCustomTalePublicStatus(Long customTaleId);

    SuccessResponse deleteCustomTale(Long customTaleId);

    Long findUserAuthorization(String accessToken);

}
