package com.anpetna.item.dto.registerItem;

import com.anpetna.coreDto.ImageDTO;
import com.anpetna.item.constant.ItemCategory;
import com.anpetna.item.constant.ItemSaleStatus;
import com.anpetna.item.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
public class RegisterItemRes {

    private Long itemId;

    private String itemName; // 상품명

    private int itemPrice;

    private String status;

    //  UI(관리자페이지)
    //  "~~~" 상품이 등록되었습니다. → [상세 보기]...
}
