package com.anpetna.coreDto;

import com.anpetna.coreDomain.ImageEntity;
import com.jayway.jsonpath.internal.Path;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.assertj.core.util.Objects;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Getter
@Builder
public class ImageDTO {

    private Long uuid;        // 이미지 고유 ID

    private String originalName; // 업로드 당시 원본 파일명

    private String fileName;     // 저장 파일명 (UUID + 확장자)

    private String url;          // 접근 가능한 URL // 서버 저장 경로

    private String contentType;  // MIME 타입 (image/png 등)

    private Integer sortOrder;   // 정렬 순서 (옵션)


    public ImageDTO(MultipartFile file) {

        // 새 파일명 생성
        String uuid = UUID.randomUUID().toString();
        String originalName = file.getOriginalFilename();
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = uuid + ext;

        // 저장 경로
        Path path = Paths.get(path, fileName);

        Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

        // URL 생성 (실제 서비스에서는 CDN/S3 기반 URL 매핑)
        String url = "https://upload-s3bucket-mbc.s3.ap-northeast-2.amazonaws.com/" + uuid;

        ImageDTO imageDTO = ImageDTO.builder()
                .originalName(originalName)
                .fileName(fileName)
                .url(url)
                .
                .build();
    }

    // Entity -> DTO 변환 생성자
    public ImageDTO(ImageEntity entity) {
        this.uuid = entity.getUuid();
        this.fileName = entity.getFileName();
        this.url = entity.getUrl();
        this.sortOrder = entity.getSortOrder();
    }

    //| 확장자        | MIME 타입     |
    //| ------------ | ------------- |
    //| .jpg / .jpeg | image/jpeg    |
    //| .png         | image/png     |
    //| .gif         | image/gif     |
    //| .bmp         | image/bmp     |
    //| .webp        | image/webp    |
    //| .tif / .tiff | image/tiff    |
    //| .svg         | image/svg+xml |
}

