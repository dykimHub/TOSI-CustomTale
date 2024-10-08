package com.tosi.customtale.service;

import com.tosi.customtale.common.exception.CustomException;
import com.tosi.customtale.common.exception.ExceptionCode;
import com.tosi.customtale.common.exception.SuccessResponse;
import com.tosi.customtale.dto.*;
import com.tosi.customtale.entity.CustomTale;
import com.tosi.customtale.entity.CustomTaleElement;
import com.tosi.customtale.repository.CustomTaleElementRepository;
import com.tosi.customtale.repository.CustomTaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomTaleServiceImpl implements CustomTaleService {
    private final S3Service s3Service;
    private final CustomTaleRepository customTaleRepository;
    private final CustomTaleElementRepository customTaleElementRepository;
    private final RestTemplate restTemplate;
    @Value("${service.user.url}")
    private String userURL;

    /**
     * 해당 회원이 생성한 커스텀 동화 목록을 반환합니다.
     *
     * @param userId   로그인한 회원 번호
     * @param pageable 페이지 번호, 페이지 크기, 정렬 기준 및 방향을 담고 있는 Pageable 객체
     * @return CustomTaleDto 객체(목록용 -> 내용 제외) 리스트
     */
    @Override
    public List<CustomTaleDto> findCustomTaleList(Long userId, Pageable pageable) {
        return customTaleRepository.findCustomTaleListByUserId(userId, pageable);
    }

    /**
     * 공개중인 커스텀 동화 목록을 반환합니다.
     *
     * @param pageable 페이지 번호, 페이지 크기, 정렬 기준 및 방향을 담고 있는 Pageable 객체
     * @return CustomTaleDto 객체(목록용 -> 내용 제외) 리스트
     */
    @Override
    public List<CustomTaleDto> findPublicCustomTaleList(Pageable pageable) {
        return customTaleRepository.findPublicCustomTaleList(pageable);
    }

    /**
     * DallE URL을 활용하여 이미지를 S3에 저장하고 S3 Key를 반환 받습니다.
     * 해당 S3 Key, 커스텀 동화 등을 활용하여 CustomTale 엔티티를 생성한 후 저장합니다.
     * 커스텀 동화를 만들 때 사용한 키워드, 배경 등을 활용하여 CustomTaleElement을 생성한 후 저장합니다.
     *
     * @param userId                     회원 번호
     * @param customTaleDetailRequestDto 커스텀 동화 정보가 담긴 CustomTaleDetailRequestDto 객체
     * @return 커스텀 동화 저장에 성공하면 SuccessResponse 객체 반환
     */
    @Transactional
    @Override
    public SuccessResponse addCustomTale(Long userId, CustomTaleDetailRequestDto customTaleDetailRequestDto) {
        String customImageS3Key = s3Service.addCustomImageToS3(customTaleDetailRequestDto.getCustomImageDallEURL(), userId);

        CustomTale customTale = CustomTale.of(
                userId,
                customTaleDetailRequestDto.getChildId(),
                customTaleDetailRequestDto.getTitle(),
                customImageS3Key,
                customTaleDetailRequestDto.getCustomTale(),
                customTaleDetailRequestDto.getIsPublic()
        );
        CustomTale savedCustomTale = customTaleRepository.save(customTale);
        Long savedCustomTaleId = savedCustomTale.getCustomTaleId();

        for (String keyword : customTaleDetailRequestDto.getKeyWord()) {
            customTaleElementRepository.save(CustomTaleElement.of(
                    savedCustomTaleId,
                    customTaleDetailRequestDto.getChildId(),
                    keyword,
                    customTaleDetailRequestDto.getBackGround())
            );

        }

        return SuccessResponse.of("커스텀 동화 저장에 성공하였습니다.");
    }

    /**
     * 커스텀 동화 상세 내용을 조회한 후, 비공개 커스텀 동화인 경우 본인 확인을 합니다.
     * 커스텀 동화 내용과 이미지 주소로 CustomTaleDetailResponseDto 객체를 생성한 후 커스텀 동화 페이지 리스트를 요청합니다.
     * 커스텀 동화 상세 페이지 리스트(#커스텀 동화 번호)를 캐시에 등록합니다.
     *
     * @param userId       회원 번호
     * @param customTaleId 커스텀 동화 번호
     * @return TalePageResponse 객체 리스트
     * @thorws 본인이 아닌 회원이 비공개 커스텀 동화를 조회한 경우
     */
    @Cacheable(value = "customTaleDetail", key = "#customTaleId")
    @Override
    public List<TalePageResponseDto> findCustomTaleDetail(Long userId, Long customTaleId) {
        CustomTaleDetailResponseDto customTaleDetailResponseDto = findCustomTaleDetailDto(customTaleId);

        // 커스텀 동화 공개 여부가 false인데, 로그인한 회원 번호와 커스텀 동화를 생성한 회원의 번호가 일치하지 않는 경우
        if (!customTaleDetailResponseDto.getIsPublic() && !customTaleDetailResponseDto.getUserId().equals(userId))
            throw new CustomException(ExceptionCode.CUSTOM_TALE_NOT_PUBLIC);

        return createCustomTalePages(CustomTaleResponseDto.ofS3(customTaleDetailResponseDto.getCustomTale(), customTaleDetailResponseDto.getCustomImageS3Key()));
    }

    /**
     * 공개 여부 수정에 성공하면 SuccessResponse를 반환합니다.
     * 커스텀 동화 상세 페이지 리스트(#커스텀 동화 번호)를 캐시에서 삭제합니다.
     *
     * @param publicStatusRequestDto 커스텀 동화 번호와 공개 여부가 담긴 PublicStatusRequestDto 객체
     * @return SuccessResponse 객체
     */
    @CacheEvict(value = "customTaleDetail", key = "#publicStatusRequestDto.customTaleId")
    @Transactional
    @Override
    public SuccessResponse modifyCustomTalePublicStatus(PublicStatusRequestDto publicStatusRequestDto) {
        findCustomTaleDetailDto(publicStatusRequestDto.getCustomTaleId());
        customTaleRepository.modifyCustomTalePublicStatus(publicStatusRequestDto.getCustomTaleId(), publicStatusRequestDto.getIsPublic());
        return SuccessResponse.of("커스텀 동화 공개 여부가 성공적으로 수정되었습니다.");
    }

    /**
     * 해당 커스텀 동화를 삭제합니다.
     * 커스텀 동화 상세 페이지 리스트(#커스텀 동화 번호)를 캐시에서 삭제합니다.
     *
     * @param customTaleId 커스텀 동화 번호
     * @return 커스텀 동화가 삭제되면 SuccessResponse를 반환합니다.
     */
    @CacheEvict(value = "customTaleDetail", key = "#customTaleId")
    @Transactional
    @Override
    public SuccessResponse deleteCustomTale(Long customTaleId) {
        findCustomTaleDetailDto(customTaleId);
        customTaleRepository.deleteById(customTaleId);
        return SuccessResponse.of("해당 커스텀 동화가 성공적으로 삭제되었습니다.");
    }

    /**
     * 커스텀 동화 페이지를 생성합니다.
     * 왼쪽 페이지는 삽화, 오른쪽 페이지는 동화 본문을 2문장씩 삽입합니다.
     *
     * @param customTaleResponseDto 커스텀 동화 내용과 이미지 S3 Key가 담긴 객체
     * @return TalePageResponse 객체 리스트
     */
    private List<TalePageResponseDto> createCustomTalePages(CustomTaleResponseDto customTaleResponseDto) {
        String[] lines = customTaleResponseDto.getCustomTale().split("\n");
        String imageS3URL = s3Service.findS3URL(customTaleResponseDto.getCustomImageS3Key());

        int pageNum = 1;
        List<TalePageResponseDto> talePageResponseDtoList = new ArrayList<>();

        for (int i = 0; i < lines.length; i += 2) {
            String line1 = lines[i];
            // line1이 마지막 문장이면 다음 문장은 빈 문장
            String line2 = (i + 1 < lines.length) ? lines[i + 1] : "";

            talePageResponseDtoList.add(
                    TalePageResponseDto.builder()
                            .leftNo(pageNum++)
                            .left(imageS3URL)
                            .rightNo(pageNum++)
                            .right(line1 + "\n" + line2)
                            .build()
            );
        }

        return talePageResponseDtoList;
    }

    /**
     * 커스텀 동화 번호로 커스텀 동화 상세 정보(내용 포함)를 조회합니다.
     *
     * @param customTaleId 커스텀 동화 번호
     * @return CustomTaleDetailDto 객체
     * @throws CustomException 커스텀 동화가 존재하지 않으면 예외 처리
     */
    private CustomTaleDetailResponseDto findCustomTaleDetailDto(Long customTaleId) {
        return customTaleRepository.findCustomTaleDetail(customTaleId)
                .orElseThrow(() -> new CustomException(ExceptionCode.CUSTOM_TALE_NOT_FOUND));
    }

    /**
     * 회원 서비스로 토큰을 보내고 인증이 완료되면 회원 번호를 반환합니다.
     *
     * @param accessToken 로그인한 회원의 토큰
     * @return 회원 번호
     * @throws CustomException 인증에 성공하지 못하면 예외 처리
     */
    @Override
    public Long findUserAuthorization(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        try {
            Long userId = restTemplate.exchange(userURL + "auth",
                    HttpMethod.GET, httpEntity, Long.class).getBody();
            return userId;
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.INVALID_TOKEN);
        }
    }

}
