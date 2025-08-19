package com.anpetna.item.service;

import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.deleteItem.DeleteItemReq;
import com.anpetna.item.dto.deleteItem.DeleteItemRes;
import com.anpetna.item.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.dto.modifyItem.ModifyItemRes;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import com.anpetna.item.dto.registerItem.ReigisterItemRes;
import com.anpetna.item.dto.searchAllItem.SearchAllItemsReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemRes;
import com.anpetna.item.repository.ItemJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemJpaRepository itemJPARepository;
    private ModelMapper modelMapper;

    @Override
    public List<SearchAllItemsReq> getAllItems(SearchAllItemsReq req) {
        return List.of();
    }

    @Override
    public SearchOneItemRes getOneItem(SearchOneItemReq req) {
        return null;
    }

    @Override
    public ReigisterItemRes registerItem(RegisterItemReq registerItemReq) {
        ItemEntity item = modelMapper.map(registerItemReq, ItemEntity.class);
        ItemEntity savedItem = itemJPARepository.save(item);
        return modelMapper.map(savedItem, ReigisterItemRes.class);
    }

    @Override
    public ModifyItemRes modifyItem(ModifyItemReq modifyItemReq) {
        ItemEntity item = modelMapper.map(modifyItemReq, ItemEntity.class);
        Optional foundItem = itemJPARepository.findById(modifyItemReq.getItemId());
        ItemEntity savedItem = itemJPARepository.save(item);
        return modelMapper.map(savedItem, ModifyItemRes.class);
    }

    @Override
    public DeleteItemRes deleteItem(DeleteItemReq deleteItemReq) {
        ItemEntity item = modelMapper.map(deleteItemReq, ItemEntity.class);
        itemJPARepository.delete(item);
        return new DeleteItemRes();
    }



}