package com.anpetna.image.service;

import com.anpetna.image.dto.ImageDTO;
import org.springframework.beans.factory.annotation.Value; //import 주의
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
public class S3ImageStorage implements FileService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String urlBase;

    public S3ImageStorage(S3Client s3Client,
                          @Value("${app.upload.bucket-name}")String bucketName,
                          @Value("${app.upload.url-base}") String urlBase) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.urlBase = urlBase;
    }

    // 업로드
    @Override
    public ImageDTO uploadFile(MultipartFile file){

        ImageDTO imageDTO = new ImageDTO(file);
        String key = urlBase + imageDTO.getFileName();
        //S3에서 key = 파일 경로 + 파일 이름 역할
        //버킷(bucket) 안에서 객체(object)를 구분하는 유니크 식별자

        try{
            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl(ObjectCannedACL.PUBLIC_READ) //접근레벨
                    .build();

            s3Client.putObject(putReq, RequestBody.fromBytes(file.getBytes()));
            imageDTO.setUrl("https://" + bucketName + ".s3.amazonaws.com/" + key);
            return imageDTO;
        }catch (IOException | S3Exception e) {
            throw new RuntimeException("S3 업로드 실패: " + key, e);
        }

    }

    // 다운로드
    @Override
    public byte[] downloadFile(String key) {

        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.getObjectAsBytes(getReq).asByteArray();

    }

    // 삭제
    @Override
    public void deleteFile(String key) {

        DeleteObjectRequest delReq = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(delReq);

    }
}

//===============================예외처리 관련 이론==========================
//체크 예외 → 컴파일러가 처리 강제, 반드시 처리, 외부 자원/IO/DB 같은 환경 의존적 문제
//언체크 예외 → 컴파일러가 강제하지 않음, 개발 실수, 논리적 버그, 런타임에서 터지는 문제

//IOException은 체크 예외라서 메서드 시그니처에 throws를 붙이거나 try-catch로 처리해야 합니다.
//귀찮다고 RuntimeException으로 감싸버리면 호출자가 예외 처리 강제를 피할 수는 있습니다.

//============== 파일 키 작명 ================
//UUID + 확장자 (UUID.ext)
//장점
//절대 중복 안 남음
//단순, S3/로컬 어디서든 안전
//단점
//원본 파일명 정보가 완전히 사라짐 → 로그나 디버깅용으로는 불편

//UUID + 원본 파일명 (UUID_originalFilename)
//장점
//원본 파일명 참고 가능 → 디버깅/운영 편리
//브라우저 다운로드 시 파일명 유추 가능
//단점
//파일명에 특수문자/공백 있으면 인코딩 필요
//길이가 너무 길어질 수 있음

//S3 업로드나 CDN 서빙 시 길이/인코딩 문제 체크 필수
//URL로 바로 접근하게 할 경우 공백 → %20 처리 필요