package com.tosi.customtale.controller;

import com.tosi.customtale.common.exception.SuccessResponse;
import com.tosi.customtale.dto.CustomResponseDto;
import com.tosi.customtale.dto.CustomTaleDto;
import com.tosi.customtale.dto.CustomTaleRequestDto;
import com.tosi.customtale.dto.TalePageResponseDto;
import com.tosi.customtale.service.CreateCustomTaleService;
import com.tosi.customtale.service.CustomTaleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping
    public ResponseEntity<CustomResponseDto> createCustomTale(@RequestHeader("Authorization") String accessToken, @RequestBody CustomTaleRequestDto customTaleRequestDto) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        CustomResponseDto customResponseDto = createCustomTaleService.createCustomTale(userId, customTaleRequestDto);
        return ResponseEntity.ok()
                .body(customResponseDto);
    }

    @Operation(summary = "커스텀 동화 각 페이지 생성")
    @PostMapping("/read")
    public ResponseEntity<List<TalePageResponseDto>> createCustomTalePages(@RequestBody CustomResponseDto customResponseDto) {
        List<TalePageResponseDto> talePageResponseDtoList = customTaleService.createCustomTalePages(customResponseDto);
        return ResponseEntity.ok()
                .body(talePageResponseDtoList);
    }

    @Operation(summary = "커스텀 동화 저장")
    @PostMapping("/save")
    public ResponseEntity<SuccessResponse> addCustomTale(@RequestHeader("Authorization") String accessToken, @RequestBody CustomTaleDto customTaleDto) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        SuccessResponse successResponse = customTaleService.addCustomTale(userId, customTaleDto);
        return ResponseEntity.ok()
                .body(successResponse);
    }


//    @Operation(summary="커스텀 동화 상세조회")
//    @GetMapping("/customtale/{customTaleId}")
//    public ResponseEntity<?> getCustomTale(HttpServletRequest request, @PathVariable Integer customTaleId) {
//        Optional<CustomTale> customTale = customTaleService.getCustomTale(customTaleId);
//        return ResponseEntity.ok(customTale);
//    }
//    @Operation(summary="내가 만든 동화 목록")
//    @GetMapping("/customtale/user")
//    public ResponseEntity<?> getCustomTalesByUserId(HttpServletRequest request, HttpServletResponse response) {
//        Integer userId = (Integer) request.getAttribute("userId");
//        List<CustomTale> customTales = customTaleService.getCustomTalesByUserId(userId);
//        System.out.println("내가 만든 동화 목록:");
//        for (CustomTale tale : customTales) {
//            System.out.println(tale.toString());
//        }
//        return ResponseEntity.ok(customTales);
//    }
//    @Operation(summary="공개중인 커스텀 동화 목록")
//    @GetMapping("/customtale")
//    public ResponseEntity<?> getCustomTales(HttpServletRequest request) {
//        List<CustomTale> customTales = customTaleService.getCustomTales();
//        return ResponseEntity.ok(customTales);
//    }

//    @Operation(summary="내가 만든 동화 공개여부 수정")
//    @PutMapping("/customtale/{customTaleId}")
//    public ResponseEntity<?> updateCustomTale(HttpServletRequest request, @PathVariable Integer customTaleId, @RequestParam boolean opened) {
//        CustomTale updatedCustomTale = customTaleService.putCustomTale(customTaleId, opened);
//        return ResponseEntity.ok(updatedCustomTale);
//    }
//    @Operation(summary="내가 만든 동화 삭제")
//    @DeleteMapping("/customtale/{customTaleId}")
//    public ResponseEntity<?> deleteCustomTale(HttpServletRequest request, @PathVariable Integer customTaleId) {
//        customTaleService.deleteCustomTale(customTaleId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}