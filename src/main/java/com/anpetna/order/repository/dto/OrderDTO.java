package com.anpetna.order.repository.dto;

import com.anpetna.item.domain.ItemEntity;

public class OrderDTO {

    private Long orderId;   // 주문 코드

    private int price;  // 주문 가격

    private int quantity;   // 주문 수량

    private Long ordersId;  // 주문 묶음 ID

}
