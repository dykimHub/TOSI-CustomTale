package com.tosi.customtale.controller;

import com.tosi.common.client.ApiUtils;
import com.tosi.common.dto.TalePageDto;
import com.tosi.common.exception.SuccessResponse;
import com.tosi.customtale.dto.CustomTaleDetailRequestDto;
import com.tosi.customtale.dto.CustomTaleDto;
import com.tosi.customtale.dto.CustomTaleRequestDto;
import com.tosi.customtale.dto.CustomTaleResponseDto;
import com.tosi.customtale.service.CreateCustomTaleService;
import com.tosi.customtale.service.CustomTaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/custom-tales")
@RequiredArgsConstructor
@RestController
public class CustomTaleController {
    private final CustomTaleService customTaleService;
    private final CreateCustomTaleService createCustomTaleService;

    @Operation(summary = "커스텀 동화 생성 요청")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = ExampleObject.customRequest
                    )
            )
    )
    @PostMapping
    public ResponseEntity<CustomTaleResponseDto> createCustomTale(@RequestHeader("Authorization") String accessToken, @RequestBody CustomTaleRequestDto customTaleRequestDto) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        CustomTaleResponseDto customTaleResponseDto = createCustomTaleService.createCustomTale(userId, customTaleRequestDto);
        return ResponseEntity.ok()
                .body(customTaleResponseDto);
    }

    @Operation(summary = "커스텀 동화 생성 후 책으로 읽기")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = ExampleObject.customResponse
                    )
            )
    )
    @PostMapping("/read")
    public ResponseEntity<List<TalePageDto>> createCustomTalePages(@RequestBody CustomTaleResponseDto customTaleResponseDto) {
        List<TalePageDto> talePageResponseDtoList = ApiUtils.createTalePages(new String[]{customTaleResponseDto.getCustomTale()}, List.of(customTaleResponseDto.getCustomTale()));
        return ResponseEntity.ok()
                .body(talePageResponseDtoList);
    }

    @Operation(summary = "내가 만든 동화 목록")
    @GetMapping
    public ResponseEntity<List<CustomTaleDto>> findCustomTales(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam(defaultValue = "0") int page) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Direction.DESC, "regDate"));
        List<CustomTaleDto> customTaleDtoList = customTaleService.findCustomTaleList(userId, pageable);
        return ResponseEntity.ok()
                .body(customTaleDtoList);
    }

    @Operation(summary = "공개중인 커스텀 동화 목록")
    @GetMapping("/public")
    public ResponseEntity<List<CustomTaleDto>> findPublicCustomTales(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
        List<CustomTaleDto> customTaleDtoList = customTaleService.findPublicCustomTaleList(pageable);
        return ResponseEntity.ok()
                .body(customTaleDtoList);
    }

    @Operation(summary = "커스텀 동화 저장")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = ExampleObject.customSaveRequest
                    )
            )
    )
    @PostMapping("/save")
    public ResponseEntity<SuccessResponse> addCustomTale(@RequestHeader("Authorization") String accessToken, @RequestBody CustomTaleDetailRequestDto customTaleDetailRequestDto) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        SuccessResponse successResponse = customTaleService.addCustomTale(userId, customTaleDetailRequestDto);
        return ResponseEntity.ok()
                .body(successResponse);
    }

    @Operation(summary = "커스텀 동화 상세 조회 후 책으로 읽기")
    @GetMapping("/{customTaleId}")
    public ResponseEntity<List<TalePageDto>> findCustomTaleDetail(@RequestHeader("Authorization") String accessToken, @Parameter(example = "3") @PathVariable Long customTaleId) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        List<TalePageDto> talePageResponseDtoList = customTaleService.findCustomTaleDetail(userId, customTaleId);
        return ResponseEntity.ok()
                .body(talePageResponseDtoList);
    }

    @Operation(summary = "커스텀 동화 공개 여부 수정")
    @PutMapping("/{customTaleId}")
    public ResponseEntity<SuccessResponse> modifyCustomTalePublicStatus(@RequestHeader("Authorization") String accessToken, @Parameter(example = "2") @PathVariable Long customTaleId) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        SuccessResponse successResponse = customTaleService.modifyCustomTalePublicStatus(userId, customTaleId);
        return ResponseEntity.ok()
                .body(successResponse);
    }

    @Operation(summary = "내가 만든 동화 삭제")
    @DeleteMapping("/{customTaleId}")
    public ResponseEntity<SuccessResponse> deleteCustomTale(@RequestHeader("Authorization") String accessToken, @Parameter(example = "2") @PathVariable Long customTaleId) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        SuccessResponse successResponse = customTaleService.deleteCustomTale(userId, customTaleId);
        return ResponseEntity.ok()
                .body(successResponse);
    }

}