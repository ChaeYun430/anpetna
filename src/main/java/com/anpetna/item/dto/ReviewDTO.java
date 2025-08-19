package com.anpetna.item.dto;

import com.anpetna.coreDomain.ImageEntity;
import com.anpetna.item.domain.ItemEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewDTO {

    private Long reviewId;

    private String content;

    private int rating;

    private LocalDateTime regDate;

    private ItemEntity itemId;

    private List<ImageEntity> images = new ArrayList<>();

}
