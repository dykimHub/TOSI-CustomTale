package com.tosi.customtale.entity;


import com.tosi.customtale.dto.CustomTaleDetailDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "custom_tales", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class CustomTale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customTaleId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "image_s3_key", nullable = false, unique = true)
    private String imageS3Key;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;
    @CreationTimestamp
    @Column(name = "reg_date", nullable = false)
    private OffsetDateTime regDate;

    @Builder
    public CustomTale(Long userId, String title, String imageS3Key, String content, Boolean isPublic) {
        this.userId = userId;
        this.title = title;
        this.imageS3Key = imageS3Key;
        this.content = content;
        this.isPublic = isPublic;
    }

    public static CustomTale of(CustomTaleDetailDto customTaleDetailDto) {
        return CustomTale.builder()
                .userId(customTaleDetailDto.getUserId())
                .title(customTaleDetailDto.getTitle())
                .imageS3Key(customTaleDetailDto.getCustomImageS3Key())
                .content(customTaleDetailDto.getCustomTale())
                .isPublic(customTaleDetailDto.getIsPublic())
                .build();

    }

}
