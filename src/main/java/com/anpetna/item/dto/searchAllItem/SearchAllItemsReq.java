package com.anpetna.item.dto.searchAllItem;

import com.anpetna.item.constant.ItemCategory;
import com.anpetna.item.constant.ItemSaleStatus;
import com.anpetna.item.constant.ItemSellStatus;
import lombok.Getter;
import org.hibernate.query.SortDirection;

@Getter
public class SearchAllItemsReq {

    private ItemSellStatus sortBySale; // 상품 판매상태

    private ItemCategory sortByCategory; // 상품 카테고리

    private Integer sortByPrice;

    private SortDirection direction; // ASC, DESC (Enum)

}
