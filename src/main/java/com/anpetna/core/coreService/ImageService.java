package com.anpetna.core.coreService;

import com.anpetna.core.coreDto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    public ImageDTO uploageImage(MultipartFile files) throws IOException;
}
