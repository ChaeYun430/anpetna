package com.anpetna.image.service;

import com.anpetna.image.dto.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalStorage implements FileService{

    private final String uploadDir;
    private final String urlBase;

    public LocalStorage(@Value("${app.upload.dir}")String uploadDir,
                        @Value("${app.upload.url-base}") String urlBase) {
        this.uploadDir = uploadDir;
        this.urlBase = urlBase;
    }

    @Override
    public ImageDTO uploadFile(MultipartFile file) {
        ImageDTO imageDTO = new ImageDTO(file);
        try {
            Path path = Paths.get(uploadDir, imageDTO.getFileName());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); //자동 덮어쓰기
            imageDTO.setUrl(urlBase + "/" + imageDTO.getFileName());
        } catch (IOException e) {
            throw new RuntimeException("로컬 업로드 실패: " +  e);
        }
        return imageDTO;
    }

    @Override
    public byte[] downloadFile(String key) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(key);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패: " + key, e);
        }
    }

    @Override
    public void deleteFile(String key) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(key);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + key, e);
        }
    }
}
