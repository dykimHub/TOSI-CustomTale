package com.tosi.customtale.repository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tosi.customtale.dto.CustomTaleDetailResponseDto;
import com.tosi.customtale.dto.CustomTaleDto;
import com.tosi.customtale.dto.QCustomTaleDetailResponseDto;
import com.tosi.customtale.dto.QCustomTaleDto;
import com.tosi.customtale.entity.CustomTale;
import com.tosi.customtale.entity.QCustomTale;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomTaleRepositoryCustomImpl implements CustomTaleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * 해당 회원 번호의 CustomTale 엔티티의 id 리스트를 반환합니다.
     * 최신순으로 정렬합니다.
     *
     * @param userId   회원 번호
     * @param pageable 페이지 번호, 페이지 크기, 정렬 기준 및 방향을 담고 있는 Pageable 객체
     * @return CustomTale 객체 id 리스트
     */
    @Override
    public List<Long> findCustomTaleIdListByUserId(Long userId, Pageable pageable) {
        QCustomTale qCustomTale = QCustomTale.customTale;
        // QCustomTale의 엔티티 타입(CustomTale)과 테이블명을 참조하여 custom-tales 테이블의 컬럼을 참조할 동적 경로 생성
        PathBuilder<CustomTale> pathBuilder = new PathBuilder<>(CustomTale.class, qCustomTale.getMetadata().getName());
        // Pageable 객체의 Sort 정보를 QueryDSL에서 사용하는 OrderSpecifier로 변환
        List<OrderSpecifier> orders = getOrderSpecifiers(pageable.getSort(), pathBuilder);

        return queryFactory.select(qCustomTale.customTaleId)
                .from(qCustomTale)
                .where(qCustomTale.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch();
    }

    /**
     * 공개 여부가 true인 CustomTale 엔티티의 id 리스트를 반환합니다.
     * 최신순으로 정렬합니다.
     *
     * @param pageable 페이지 번호, 페이지 크기, 정렬 기준 및 방향을 담고 있는 Pageable 객체
     * @return CustomTaleDto 객체 리스트
     */
    @Override
    public List<Long> findPublicCustomTaleIdList(Pageable pageable) {
        QCustomTale qCustomTale = QCustomTale.customTale;
        // QCustomTale의 엔티티 타입(CustomTale)과 테이블명을 참조하여 custom-tales 테이블의 컬럼을 참조할 동적 경로 생성
        PathBuilder<CustomTale> pathBuilder = new PathBuilder<>(CustomTale.class, qCustomTale.getMetadata().getName());
        // Pageable 객체의 Sort 정보를 QueryDSL에서 사용하는 OrderSpecifier로 변환
        List<OrderSpecifier> orders = getOrderSpecifiers(pageable.getSort(), pathBuilder);

        return queryFactory.select(qCustomTale.customTaleId)
                .from(qCustomTale)
                .where(qCustomTale.isPublic.eq(true))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch();
    }

    /**
     * 해당 번호 목록에 해당하는 CustomTale 엔티티 리스트를 반환합니다.
     *
     * @param customTaleIds 커스텀 동화 번호 목록
     * @return CustomTaleDto 객체 리스트
     */
    @Override
    public List<CustomTaleDto> findCustomTaleList(List<Long> customTaleIds) {
        QCustomTale qCustomTale = QCustomTale.customTale;
        return queryFactory.select(new QCustomTaleDto(
                        qCustomTale.customTaleId,
                        qCustomTale.title,
                        qCustomTale.imageS3Key,
                        qCustomTale.isPublic
                ))
                .from(qCustomTale)
                .where(qCustomTale.customTaleId.in(customTaleIds))
                .fetch();
    }

    /**
     * 해당 번호의 CustomTale 엔티티를 CustomDetailDto로 변환하여 반환합니다.
     *
     * @param customTaleId 커스텀 동화 번호
     * @return Optional로 감싼 CustomTaleDetailDto 객체
     */
    @Override
    public Optional<CustomTaleDetailResponseDto> findCustomTaleDetail(Long customTaleId) {
        QCustomTale qCustomTale = QCustomTale.customTale;
        return Optional.ofNullable(queryFactory.select(new QCustomTaleDetailResponseDto(
                        qCustomTale.userId,
                        qCustomTale.childId,
                        qCustomTale.title,
                        qCustomTale.content,
                        qCustomTale.imageS3Key,
                        qCustomTale.isPublic))
                .from(qCustomTale)
                .where(qCustomTale.customTaleId.eq(customTaleId))
                .fetchOne()
        );

    }

    /**
     * Sort 객체(정렬 기준 리스트)를 OrderSpecifier 객체 리스트로 변환합니다.
     *
     * @param sort        Sort 객체
     * @param pathBuilder 정의된 경로
     * @return QueryDSL 쿼리에서 사용하는 정렬 순서를 나타내는 OrderSpecifier 객체 리스트
     */
    private List<OrderSpecifier> getOrderSpecifiers(Sort sort, PathBuilder<CustomTale> pathBuilder) {
        return sort.stream()
                .map(order -> new OrderSpecifier(
                        order.isAscending() ? Order.ASC : Order.DESC, // 필드의 정렬 방향 설정
                        pathBuilder.get(order.getProperty()) // 필드에 대한 경로 가져오기
                ))
                .toList();
    }
}
