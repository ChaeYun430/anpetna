package com.anpetna.item.repository;

import com.anpetna.item.domain.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long>, ItemRepositoryCustom {




/*    ItemEntity findByName(String name);

    List<Item> findByItemName(String itemName);

    Optional<Item> findByItemId(Long itemId);

    void deleteById(Long id);*/


}
