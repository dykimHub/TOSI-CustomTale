package com.tosi.customtale.repository;

import com.tosi.customtale.dto.CustomTaleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CustomTaleRepositoryCustom {

    List<CustomTaleDto> findCustomTaleListByUserId(Long userId, Pageable pageable);
}
