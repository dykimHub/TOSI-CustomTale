package com.tosi.customtale.service;

import com.amazonaws.services.s3.model.S3Object;

public interface S3Service {
    S3Object findS3Object(String s3Key);

    String findS3URL(String s3key);

    String addCustomImageToS3(String customImageURL, Long userId);
}
