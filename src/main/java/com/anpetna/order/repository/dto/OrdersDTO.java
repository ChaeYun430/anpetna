package com.anpetna.order.repository.dto;

import com.anpetna.order.domain.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    private Long ordersId;

    private String memberId;

    private String cardId;

    private int totalPrice; // 총액

    private int itemQuantity;   // 총 수량


}
