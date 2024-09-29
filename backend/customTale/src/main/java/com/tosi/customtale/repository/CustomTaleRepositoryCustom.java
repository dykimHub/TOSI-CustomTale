package com.tosi.customtale.repository;

import com.tosi.customtale.dto.CustomTaleDetailDto;
import com.tosi.customtale.dto.CustomTaleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface CustomTaleRepositoryCustom {

    List<CustomTaleDto> findCustomTaleListByUserId(Long userId, Pageable pageable);

    List<CustomTaleDto> findPublicCustomTaleList(Pageable pageable);
}
