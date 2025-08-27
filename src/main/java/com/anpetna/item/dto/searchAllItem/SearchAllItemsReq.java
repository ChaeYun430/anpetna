package com.anpetna.item.dto.searchAllItem;

import com.anpetna.item.constant.ItemCategory;
import com.anpetna.item.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;

@Builder
@Getter
public class SearchAllItemsReq {

    //  정렬용 필드
    private ItemSellStatus sortBySale; // 상품 판매상태

    private ItemCategory sortByCategory; // 상품 카테고리

    private Sort.Direction orderByPriceDir; // 가격순


    //  Pagenation
    @Builder.Default
    private int page = 0;   // 페이지 번호

    @Builder.Default
    private int size = 20;  // 페이지 크기

    @Builder.Default
    private Sort.Direction sort = Sort.Direction.DESC; // 정렬
}
