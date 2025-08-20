package com.anpetna.item.dto.registerItem;

import com.anpetna.coreDto.ImageDTO;
import com.anpetna.item.constant.ItemCategory;
import com.anpetna.item.constant.ItemSaleStatus;
import com.anpetna.item.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class RegisterItemReq {

    //  8개

    private String itemName; // 상품명

    private int itemPrice; // 가격

    private int itemStock; // 재고수량

    private String itemDetail; // 상품 상세설명

    private ItemSellStatus itemSellStatus; // 상품 판매상태

    private ItemSaleStatus itemSaleStatus; // 상품 세일상태

    private ItemCategory itemCategory; // 상품 카테고리

    @Builder.Default
    private List<ImageDTO> itemImages = new ArrayList<>();

    //썸네일 어케하지

}
