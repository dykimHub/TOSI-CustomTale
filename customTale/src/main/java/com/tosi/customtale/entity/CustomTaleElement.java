package com.tosi.customtale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "custom_tales_elements", indexes = {
        @Index(name = "idx_child_id", columnList = "child_id")
})
public class CustomTaleElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long elementId;
    @Column(name = "custom_tale_id", nullable = false)
    private Long customTaleId;
    @Column(name = "child_id", nullable = false)
    private Long childId;
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "background")
    private String background;

    @Builder
    public CustomTaleElement(Long customTaleId, Long childId, String keyword, String background) {
        this.customTaleId = customTaleId;
        this.childId = childId;
        this.keyword = keyword;
        this.background = background;
    }

    public static CustomTaleElement of(Long customTaleId, Long childId, String keyword, String background) {
        return CustomTaleElement.builder()
                .customTaleId(customTaleId)
                .childId(childId)
                .keyword(keyword)
                .background(background)
                .build();
    }
}
