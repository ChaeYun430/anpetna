package com.anpetna.order.repository.dto.deleteOrder;


import com.anpetna.order.repository.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterOrderReq {

    private String memberId;    // 주문자

    private String cardId;      // 결제 카드

    private List<OrderDTO> items;   // 주문 품목





}
