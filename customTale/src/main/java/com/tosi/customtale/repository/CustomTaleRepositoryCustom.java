package com.tosi.customtale.repository;

import com.tosi.customtale.dto.CustomTaleDetailResponseDto;
import com.tosi.customtale.dto.CustomTaleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface CustomTaleRepositoryCustom {
    
    List<Long> findCustomTaleIdListByUserId(Long userId, Pageable pageable);

    List<CustomTaleDto> findCustomTaleList(List<Long> customTaleIds);

    List<CustomTaleDto> findPublicCustomTaleList(Pageable pageable);

    Optional<CustomTaleDetailResponseDto> findCustomTaleDetail(Long customTaleId);
}
