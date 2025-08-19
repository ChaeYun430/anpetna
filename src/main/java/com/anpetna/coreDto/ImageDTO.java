package com.anpetna.coreDto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDTO {
    private Long uuid;
    private String fileName;
    private String url;
    private Integer sortOrder;
}
