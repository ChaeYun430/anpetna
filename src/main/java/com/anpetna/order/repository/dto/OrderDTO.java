package com.anpetna.order.repository.dto;

import com.anpetna.item.domain.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderId;   // 주문 코드

    private Long itemId;    // ItemEntity PK

    private int price;  // 단가

    private int quantity;   // 주문 수량

    private int lineAmount; // 단가 * 수량

}
