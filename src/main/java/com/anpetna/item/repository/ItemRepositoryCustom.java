package com.anpetna.item.repository;

import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.searchAllItem.SearchAllItemsReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepositoryCustom {

    Page<ItemEntity> sortByCategory(Pageable pageable);

    Page<ItemEntity> sortBySales(Pageable pageable);

    Page<ItemEntity> orderByPriceDir(Pageable pageable);

    List<ItemEntity> searchAll(SearchAllItemsReq req);
}
