package com.anpetna.core.coreDto;

import com.anpetna.core.constant.ImageUsage;
import com.anpetna.core.coreDomain.ImageEntity;
import com.jayway.jsonpath.internal.Path;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Getter
@Builder
public class ImageDTO {

    @NotNull
    private String uuid;        // 이미지 고유 ID
    @NotNull
    private String originalName; // 업로드 당시 원본 파일명
    @NotNull
    private String fileName;     // 저장 파일명 (UUID + 확장자)
    @NotNull
    private String contentType;  // MIME 타입 (image/png 등)

    private String url;          // 접근 가능한 URL(실제 서비스에서는 CDN/S3 기반 URL 매핑)
    private String path;         // 서버 저장 경로

    @Builder.Default
    private ImageUsage usage = ImageUsage.ETC;

    private Integer sortOrder;   // 정렬 순서 (옵션)

    public ImageDTO(MultipartFile file) {
        // 새 파일명 생성
        this.uuid = UUID.randomUUID().toString();
        this.originalName = file.getOriginalFilename();
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); //확장자
        this.fileName = uuid + ext;
        this.contentType = "image/" + ext.substring(ext.lastIndexOf(".") + 1);
    }

    public ImageDTO from(String url, String path) {
        this.url = url;
        this.path = path;
        return this;
    }

    // Entity -> DTO 변환 생성자
    public ImageDTO(ImageEntity entity) {
        this.uuid = String.valueOf(entity.getUuid());
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