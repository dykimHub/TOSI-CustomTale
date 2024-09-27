package com.tosi.customtale.controller;
import com.ssafy.tosi.s3.S3Controller;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomTaleController {
//    private final CustomTaleService customTaleService;
//    private final S3Controller s3Controller;
//    @Autowired
//    public CustomTaleController(HttpServletRequest request, CustomTaleService customTaleService, S3Controller s3Controller) {
//        this.customTaleService = customTaleService;
//        this.s3Controller = s3Controller;
//    }
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
//    @Operation(summary="내가 만든 동화 저장")
//    @PostMapping("/customtale")
//    public ResponseEntity<?> insertCustomTale(HttpServletRequest request, HttpServletResponse response, @RequestBody CustomTale customTale) {
//        Integer userId = (Integer) request.getAttribute("userId");
//        customTale.setUserId(userId);
//        System.out.println(customTale.getThumbnail());
//        customTale.setThumbnail("https://talebucket.s3.ap-northeast-2.amazonaws.com/"+s3Controller.uploadImageToS3(customTale.getThumbnail()));
//        System.out.println(customTale.getThumbnail());
//        CustomTale savedCustomTale = customTaleService.postCustomTale(customTale);
//        return new ResponseEntity<>(savedCustomTale, HttpStatus.CREATED);
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
//    @PostMapping("/customtale/read")
//    public ResponseEntity<?> read(HttpServletRequest request, @RequestBody String string) {
//        try {
//            if(string.charAt(0)=='"' && string.charAt(string.length()-1)=='"'){
//                string = string.substring(1, string.length() - 1);
//            }
//            // string = string.replaceAll("\\\\|n\\\\n", "");
//            String splitted_contents = customTaleService.split_sentences(string); // 문장 분리
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.TEXT_PLAIN);
////            headers.set(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8"); // 인코딩 설정
//            List<Page> pages = customTaleService.paging(splitted_contents); // 페이지 형식으로 변경
////            return new ResponseEntity<List<Page>>(pages, headers, HttpStatus.OK);
//            return new ResponseEntity<List<Page>>(pages, HttpStatus.OK);
//        }  catch (Exception e) {
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}