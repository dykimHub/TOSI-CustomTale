package com.tosi.customtale.entity;


import com.tosi.customtale.dto.CustomTaleDto;
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
@Table(name = "custom_tales")
public class CustomTale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customTaleId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "image_s3_key", nullable = false)
    private String imageS3Key;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    @CreationTimestamp
    @Column(name = "reg_date", nullable = false)
    private OffsetDateTime regDate;

    @Builder
    public CustomTale(int userId, String title, String imageS3Key, String content, boolean isPublic) {
        this.userId = userId;
        this.title = title;
        this.imageS3Key = imageS3Key;
        this.content = content;
        this.isPublic = isPublic;
    }

    public static CustomTale of(CustomTaleDto customTaleDto) {
        return CustomTale.builder()
                .userId(customTaleDto.getUserId())
                .title(customTaleDto.getTitle())
                .imageS3Key(customTaleDto.getCustomResponseDto().getCustomImageS3Key())
                .content(customTaleDto.getCustomResponseDto().getCustomTale())
                .isPublic(customTaleDto.isPublic())
                .build();

    }

}
