package com.anpetna.item.dto.modifyItem;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ModifyItemRes {

    //  UI(관리자 페이지)
    //  성공 알림 표시
    //  상품 리스트 화면 자동 갱신
    private Long itemId;
    private String itemName;          // 수정된 상품명
    private Integer itemPrice;        // 수정된 가격
    private Integer itemStock;        // 수정된 재고
    private LocalDateTime latestDate;
    private String res;        // "UPDATED"
}
