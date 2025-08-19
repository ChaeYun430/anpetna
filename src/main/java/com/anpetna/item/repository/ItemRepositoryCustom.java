package com.anpetna.item.repository;

import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.SearchAllReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepositoryCustom {

    List<ItemEntity> sortByCategory(SearchAllReq searchAllDTO);

    List<ItemEntity> orderByPrice(SearchAllReq searchAllDTO);

    List<ItemEntity> orderBySales(SearchAllReq searchAllDTO);


}
