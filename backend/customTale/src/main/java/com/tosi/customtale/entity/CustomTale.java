package com.tosi.customtale.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "customtales")
public class CustomTale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customtale_id", nullable = false)
    private int customTaleId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "image_s3_key", nullable = false)
    private String imageS3Key;
    @Column(name = "content_s3_key", nullable = false)
    private String contentS3URL;
    @Column(name = "is_public")
    private boolean isPublic; 


}
