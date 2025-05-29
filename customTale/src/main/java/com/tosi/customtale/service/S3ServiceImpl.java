package com.tosi.customtale.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements S3Service {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * AWS S3에서 해당 S3 Key의 S3Object를 반환합니다.
     *
     * @param s3Key S3 객체의 Key
     * @return S3Object
     */
    @Override
    public S3Object findS3Object(String s3Key) {
        return amazonS3.getObject(new GetObjectRequest(bucketName, s3Key));
    }

    /**
     * AWS S3에서 해당 버킷 이름과 S3 Key로 S3 객체 URL을 생성합니다.
     *
     * @param s3key S3 객체의 Key
     * @return S3 객체의 URL
     */
    @Override
    public String findS3URL(String s3key) {
        return amazonS3.getUrl(bucketName, s3key).toString();
    }

    /**
     * 커스텀 이미지 URL에서 스트림을 추출하여 S3에 저장합니다.
     * 저장된 이미지의 S3Key를 반환합니다.
     *
     * @param customImageURL OpenAI API가 생성한 이미지 URL
     * @param userId         로그인한 회원 번호
     * @return 저장된 이미지의 S3 Key
     */
    @Override
    public String uploadImage(String customImageURL, Long userId) {
        InputStream inputStream = getInputStreamFromURL(customImageURL);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/png");
        // 파일명: customTaleImgList/회원번호/랜덤식별자
        String fileName = "customTaleImgList/" + userId + "/" + UUID.randomUUID();

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata));

        return findS3Object(fileName).getKey();


    }

    /**
     * 이미지 URL에서 InputStream을 추출합니다.
     *
     * @param customImageURL OpenAPI AI가 생성한 이미지의 URL
     * @return URL에서 추출한 InputStream(이미지 데이터를 읽기 위한 스트림)
     * @throws RuntimeException InputStream을 추출할 때 오류가 발생한 경우
     */
    private InputStream getInputStreamFromURL(String customImageURL) {
        try {
            URL url = new URL(customImageURL); // URL 객체 생성
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // HTTP 연결 생성
            connection.setRequestMethod("GET"); // GET 요청
            return connection.getInputStream();

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }


}
