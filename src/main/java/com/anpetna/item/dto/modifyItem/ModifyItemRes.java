package com.anpetna.item.dto.modifyItem;

import java.time.LocalDateTime;

public class ModifyItemRes {

    //  UI(관리자 페이지)
    //  성공 알림 표시
    //  상품 리스트 화면 자동 갱신
    private Long id;
    private String name;          // 수정된 상품명
    private Integer price;        // 수정된 가격
    private Integer stock;        // 수정된 재고
    private LocalDateTime updatedAt;
    private String status;        // "UPDATED"

}
