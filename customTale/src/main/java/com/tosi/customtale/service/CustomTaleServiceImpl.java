package com.tosi.customtale.service;

import com.tosi.common.client.ApiClient;
import com.tosi.common.constants.ApiPaths;
import com.tosi.common.constants.CachePrefix;
import com.tosi.common.exception.CustomException;
import com.tosi.common.exception.SuccessResponse;
import com.tosi.common.service.CacheService;
import com.tosi.common.service.S3Service;
import com.tosi.customtale.common.exception.ExceptionCode;
import com.tosi.customtale.dto.CustomTaleDetailRequestDto;
import com.tosi.customtale.dto.CustomTaleDetailResponseDto;
import com.tosi.customtale.dto.CustomTaleDto;
import com.tosi.customtale.dto.TalePageResponseDto;
import com.tosi.customtale.entity.CustomTale;
import com.tosi.customtale.entity.CustomTaleElement;
import com.tosi.customtale.repository.CustomTaleElementRepository;
import com.tosi.customtale.repository.CustomTaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomTaleServiceImpl implements CustomTaleService {
    private final S3Service s3Service;
    private final CacheService cacheService;
    private final CustomTaleRepository customTaleRepository;
    private final CustomTaleElementRepository customTaleElementRepository;
    private final ApiClient apiClient;
    @Value("${service.user.url}")
    private String userURL;

    /**
     * 해당 회원이 생성한 커스텀 동화 목록을 반환합니다.
     * 커스텀 동화 ID 목록을 DB에서 페이지네이션하여 조회합니다.
     * 캐시에서 커스텀 동화 객체를 조회를 시도하며, 없을 경우 DB에서 객체를 조회하여 반환합니다.
     *
     * @param userId   로그인한 회원 번호
     * @param pageable 페이지 번호, 페이지 크기, 정렬 기준 및 방향을 담고 있는 Pageable 객체
     * @return CustomTaleDto 객체 리스트
     */
    @Override
    public List<CustomTaleDto> findCustomTaleList(Long userId, Pageable pageable) {
        // 해당 페이지의 커스텀 동화 ID 목록
        List<Long> customTaleIds = customTaleRepository.findCustomTaleIdListByUserId(userId, pageable);
        // 커스텀 동화 ID 목록을 CacheKey 목록으로 변환 후 캐시 조회
        List<CustomTaleDto> cachedCustomTaleDtoList = cacheService.getMultiCaches(CachePrefix.CUSTOM_TALE.buildCacheKeys(customTaleIds), CustomTaleDto.class);
        if (customTaleIds.size() == cachedCustomTaleDtoList.size())
            return cachedCustomTaleDtoList;
        // 캐시에서 조회한 커스텀 동화 객체 Map을 생성
        Map<Long, CustomTaleDto> cachedCustomTaleDtoMap = createCustomTaleDtoMap(cachedCustomTaleDtoList, true);
        // DB에서 캐시에 없는 커스텀 동화를 조회
        List<CustomTaleDto> missingCustomTaleDtoList = findMissingCustomTaleList(customTaleIds, cachedCustomTaleDtoMap);
        /// DB에서 조회한 커스텀 동화 객체 Map을 생성
        Map<Long, CustomTaleDto> missingCustomTaleDtoMap = createCustomTaleDtoMap(missingCustomTaleDtoList, false);
        // DB에서 조회한 커스텀 동화로 캐시용 Map을 생성하고 저장
        Map<String, CustomTaleDto> cacheMap = cacheService.createCacheMap(missingCustomTaleDtoMap, CachePrefix.CUSTOM_TALE);
        cacheService.setMultiCaches(cacheMap, 6, TimeUnit.HOURS);
        // ID 리스트 순서대로 캐시맵, DB맵을 조회하여 최종 커스텀 동화 리스트를 생성합니다.
        return customTaleIds.stream()
                .map(id -> cachedCustomTaleDtoMap.getOrDefault(id, missingCustomTaleDtoMap.get(id)))
                .toList();

    }

    /**
     * 공개중인 커스텀 동화 목록을 반환합니다.
     * 추천 목록 요청은 4개, 일반 목록 요청은 9개를 반환합니다.
     * 캐시에서 커스텀 동화 객체를 조회를 시도하며, 없을 경우 DB에서 객체를 조회하여 반환합니다.
     *
     * @param pageable 페이지 번호, 페이지 크기, 정렬 기준 및 방향을 담고 있는 Pageable 객체
     * @return CustomTaleDto 객체(목록용 -> 내용 제외) 리스트
     */
    @Override
    public List<CustomTaleDto> findPublicCustomTaleList(Pageable pageable) {
        // 해당 페이지의 커스텀 동화 ID 목록
        List<Long> customTaleIds = customTaleRepository.findPublicCustomTaleIdList(pageable);
        // 커스텀 동화 ID 목록을 CacheKey 목록으로 변환 후 캐시 조회
        List<CustomTaleDto> cachedCustomTaleDtos = cacheService.getMultiCaches(CachePrefix.CUSTOM_TALE.buildCacheKeys(customTaleIds), CustomTaleDto.class);
        if (customTaleIds.size() == cachedCustomTaleDtos.size())
            return cachedCustomTaleDtos;
        // 캐시에서 조회한 커스텀 동화 객체 Map을 생성
        Map<Long, CustomTaleDto> cachedCustomTaleDtoMap = createCustomTaleDtoMap(cachedCustomTaleDtos, true);
        // DB에서 캐시에 없는 커스텀 동화를 조회
        List<CustomTaleDto> missingCustomTaleDtos = findMissingCustomTaleList(customTaleIds, cachedCustomTaleDtoMap);
        // DB에서 조회한 커스텀 동화 객체 Map을 생성
        Map<Long, CustomTaleDto> missingCustomTaleDtoMap = createCustomTaleDtoMap(missingCustomTaleDtos, false);
        // ID 리스트 순서대로 캐시 Map, DB Map을 조회하여 최종 커스텀 동화 리스트를 생성
        return customTaleIds.stream()
                .map(id -> cachedCustomTaleDtoMap.getOrDefault(id, missingCustomTaleDtoMap.get(id)))
                .toList();
    }

    /**
     * 커스텀 동화 객체 리스트를 Map으로 변환하여 반환합니다.
     *
     * @param customTaleDtoList 커스텀 동화 객체 리스트
     * @param s3UrlExists       객체에 S3 URL이 존재하는지 여부
     * @return key: 커스텀 동화 객체 Id, value: 커스텀 동화 객체
     */
    private Map<Long, CustomTaleDto> createCustomTaleDtoMap(List<CustomTaleDto> customTaleDtoList, boolean s3UrlExists) {
        return customTaleDtoList.stream()
                .collect(Collectors.toMap(
                        CustomTaleDto::getCustomTaleId, // key
                        c -> s3UrlExists ? c : c.toWithoutS3Key(s3Service.findS3URL(c.getImageS3Key())) // value
                ));
    }

    /**
     * 캐시에 없는 커스텀 동화를 DB에서 조회하여 반환합니다.
     *
     * @param customTaleIds          커스텀 동화 객체 Id 목록
     * @param cachedCustomTaleDtoMap 캐싱된 커스텀 동화 객체 Map
     * @return 커스텀 동화 객체 리스트
     * @throws CustomException DB에서 커스텀 동화 목록을 찾을 수 없으면 예외 처리
     */
    private List<CustomTaleDto> findMissingCustomTaleList(List<Long> customTaleIds, Map<Long, CustomTaleDto> cachedCustomTaleDtoMap) {
        // Map에 없는 id 필터링
        List<Long> missingCustomTaleIds = customTaleIds.stream()
                .filter(id -> !cachedCustomTaleDtoMap.containsKey(id))
                .toList();
        // DB에서 해당 id 목록 조회
        List<CustomTaleDto> missingCustomTaleDtoList = customTaleRepository.findCustomTaleList(missingCustomTaleIds);
        if (missingCustomTaleDtoList.isEmpty())
            throw new CustomException(ExceptionCode.PARTIAL_CUSTOM_TALE_NOT_FOUND);

        return missingCustomTaleDtoList;

    }

    /**
     * DallE URL을 활용하여 이미지를 S3에 저장하고 S3 Key를 반환 받습니다.
     * 해당 S3 Key, 커스텀 동화 등을 활용하여 CustomTale 엔티티를 생성한 후 저장합니다.
     * 커스텀 동화를 만들 때 사용한 키워드, 배경 등을 활용하여 CustomTaleElement을 생성한 후 저장합니다.
     * 최신 등록된 커스텀 동화 개요 및 상세 객체를 1시간 동안 캐싱하여 공개중인 동화 목록에서 초기 트래픽 급증 처리
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
                            customTaleDetailRequestDto.getBackGround()
                    )
            );

        }

        cacheService.setCache(CachePrefix.CUSTOM_TALE.buildCacheKey(savedCustomTaleId),
                CustomTaleDto.of(savedCustomTaleId, savedCustomTale.getTitle(), s3Service.findS3URL(savedCustomTale.getImageS3Key()), savedCustomTale.getIsPublic()),
                1, TimeUnit.HOURS);

        cacheService.setCache(CachePrefix.CUSTOM_TALE_DETAIL.buildCacheKey(savedCustomTaleId),
                CustomTaleDetailResponseDto.of(savedCustomTale, s3Service.findS3URL(savedCustomTale.getImageS3Key())),
                1, TimeUnit.HOURS);

        return SuccessResponse.of("커스텀 동화 저장에 성공하였습니다.");
    }

    /**
     * 커스텀 동화 상세 내용을 페이지 객체로 반환합니다.
     * 캐시에서 먼저 조회하고 없으면 DB에서 찾습니다.
     * 비공개 커스텀 동화인 경우 본인 확인을 합니다.
     * 커스텀 동화 내용과 이미지 주소로 CustomTaleDetailResponseDto 객체를 생성한 후 커스텀 동화 페이지 리스트를 요청합니다.
     *
     * @param userId       회원 번호
     * @param customTaleId 커스텀 동화 번호
     * @return TalePageResponse 객체 리스트
     * @throws CustomException 본인이 아닌 회원이 비공개 커스텀 동화를 조회한 경우
     */
    @Override
    public List<TalePageResponseDto> findCustomTaleDetail(Long userId, Long customTaleId) {
        CustomTaleDetailResponseDto customTaleDetailResponseDto = cacheService.getCache(CachePrefix.CUSTOM_TALE_DETAIL.buildCacheKey(customTaleId), CustomTaleDetailResponseDto.class)
                .or(() -> customTaleRepository.findCustomTaleDetail(customTaleId)
                        .map(c -> c.toWithoutS3Key(s3Service.findS3URL(c.getCustomImageS3Key()))))
                .orElseThrow(() -> new CustomException(ExceptionCode.CUSTOM_TALE_NOT_FOUND));

        // 커스텀 동화 공개 여부가 false인데, 로그인한 회원 번호와 커스텀 동화를 생성한 회원의 번호가 일치하지 않는 경우
        if (!customTaleDetailResponseDto.getIsPublic() && !customTaleDetailResponseDto.getUserId().equals(userId))
            throw new CustomException(ExceptionCode.CUSTOM_TALE_NOT_PUBLIC);

        String imageS3URL = s3Service.findS3URL(customTaleDetailResponseDto.getCustomImageS3Key());
        return createPages(customTaleDetailResponseDto.getCustomTale(), imageS3URL);
    }

    /**
     * 커스텀 동화 페이지를 생성합니다.
     * 왼쪽 페이지는 삽화, 오른쪽 페이지는 동화 본문을 2문장씩 삽입합니다.
     *
     * @param customTale 커스텀 동화
     * @param imageURL   이미지 주소
     * @return TalePageResponse 객체 리스트
     */
    @Override
    public List<TalePageResponseDto> createPages(String customTale, String imageURL) {
        String[] lines = customTale.split("\n");

        int pageNum = 1;
        List<TalePageResponseDto> talePageResponseDtoList = new ArrayList<>();

        for (int i = 0; i < lines.length; i += 2) {
            String line1 = lines[i];
            // line1이 마지막 문장이면 다음 문장은 빈 문장
            String line2 = (i + 1 < lines.length) ? lines[i + 1] : "";

            talePageResponseDtoList.add(
                    TalePageResponseDto.builder()
                            .leftNo(pageNum++)
                            .left(imageURL)
                            .rightNo(pageNum++)
                            .right(line1 + "\n" + line2)
                            .build()
            );
        }

        return talePageResponseDtoList;
    }

    /**
     * 공개 여부 수정에 성공하면 SuccessResponse를 반환합니다.
     * 커스텀 동화 상세 객체를 캐시에서 삭제합니다.
     *
     * @param customTaleId 커스텀 동화 번호와 공개 여부가 담긴 PublicStatusRequestDto 객체
     * @return SuccessResponse 객체
     */
    @Transactional
    @Override
    public SuccessResponse modifyCustomTalePublicStatus(Long customTaleId) {
        if (!customTaleRepository.existsById(customTaleId))
            throw new CustomException(ExceptionCode.CUSTOM_TALE_NOT_FOUND);

        cacheService.deleteCache(CachePrefix.CUSTOM_TALE_DETAIL.buildCacheKey(customTaleId));
        customTaleRepository.modifyCustomTalePublicStatus(customTaleId);
        return SuccessResponse.of("커스텀 동화 공개 여부가 성공적으로 수정되었습니다.");
    }

    /**
     * 해당 커스텀 동화를 삭제합니다.
     * 커스텀 동화 상세 객체를 캐시에서 삭제합니다.
     *
     * @param customTaleId 커스텀 동화 번호
     * @return 커스텀 동화가 삭제되면 SuccessResponse를 반환합니다.
     */
    @Transactional
    @Override
    public SuccessResponse deleteCustomTale(Long customTaleId) {
        if (!customTaleRepository.existsById(customTaleId))
            throw new CustomException(ExceptionCode.CUSTOM_TALE_NOT_FOUND);

        cacheService.deleteCache(CachePrefix.CUSTOM_TALE_DETAIL.buildCacheKey(customTaleId));
        customTaleRepository.deleteById(customTaleId);
        return SuccessResponse.of("해당 커스텀 동화가 성공적으로 삭제되었습니다.");
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
        Long userId = apiClient.getObject(ApiPaths.AUTH.buildPath(userURL), headers, Long.class);
        return userId;

    }

}
