package com.anpetna.item.service;

import com.anpetna.item.repository.dto.ItemDTO;
import com.anpetna.item.repository.dto.deleteItem.DeleteItemReq;
import com.anpetna.item.repository.dto.deleteItem.DeleteItemRes;
import com.anpetna.item.repository.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.repository.dto.modifyItem.ModifyItemRes;
import com.anpetna.item.repository.dto.registerItem.RegisterItemReq;
import com.anpetna.item.repository.dto.registerItem.RegisterItemRes;
import com.anpetna.item.repository.ItemJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemJpaRepository itemJPARepository;

/*
    private final JPAQueryFactory queryFactory = new JPAQueryFactory();

*/

    @Override
    public List<ItemDTO> getAllItems() {


        return null;
    }

    @Override
    public ItemDTO getOneItem(Long itemId) {

        return null;
    }

    @Override
    public RegisterItemRes registerItem(RegisterItemReq registerItemReq) {
return null;
    }


    @Override
    public ModifyItemRes modifyItem(ModifyItemReq modifyItemReq) {

        return null;
    }

    @Override
    public DeleteItemRes deleteItem(DeleteItemReq deleteItemReq) {

        itemJPARepository.deleteById(deleteItemReq.getItemId());

        return null;
    }

}