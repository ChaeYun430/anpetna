package com.anpetna.item.service;

import com.anpetna.item.config.ItemMapper;
import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.deleteItem.DeleteItemReq;
import com.anpetna.item.dto.deleteItem.DeleteItemRes;
import com.anpetna.item.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.dto.modifyItem.ModifyItemRes;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import com.anpetna.item.dto.registerItem.RegisterItemRes;
import com.anpetna.item.dto.searchAllItem.SearchAllItemsReq;
import com.anpetna.item.dto.searchAllItem.SearchAllItemsRes;
import com.anpetna.item.dto.searchOneItem.SearchOneItemReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemRes;
import com.anpetna.item.repository.ItemJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemJpaRepository repository;
    private ModelMapper modelMapper = new ModelMapper();
    private ItemMapper itemMapper = new ItemMapper();

/*    @Override
    public List<SearchAllItemsRes> getAllItems(SearchAllItemsReq req) {
        List<ItemEntity> sortByCategory = repository.sortByCategory(req);
        List<ItemEntity> orderByPrice = repository.orderByPrice(req);
        List<ItemEntity> orderBySales = repository.orderBySales(req);
        //return modelMapper.map(sortByCategory, List.class);
        return new ArrayList<>();
    }*/

    @Override
    public SearchOneItemRes getOneItem(SearchOneItemReq req) {
        ItemEntity item = modelMapper.map(req, ItemEntity.class);
        Optional foundItem = repository.findById(req.getItemId());
        return modelMapper.map(foundItem, SearchOneItemRes.class);
    }

    @Override
    public RegisterItemRes registerItem(RegisterItemReq req) {
        ItemEntity item = modelMapper.map(req, ItemEntity.class);
        ItemEntity savedItem = repository.save(item);
        RegisterItemRes res =  modelMapper.map(savedItem, RegisterItemRes.class);
                res.setStatus("registeres");
        return res;
    }

    @Override
    public ModifyItemRes modifyItem(ModifyItemReq req) {
        return null;
    }

    @Override
    public DeleteItemRes deleteItem(DeleteItemReq req) {
        return null;
    }

 /*   @Override
    public ModifyItemRes modifyItem(ModifyItemReq req) {
        ItemEntity item = itemMapper.map(req, ItemEntity.class);
        Optional foundItem = repository.findById(req.getItemId());
        ItemEntity savedItem = repository.save(item);
        return mapper.map(savedItem, ModifyItemRes.class);
    }

    @Override
    public DeleteItemRes deleteItem(DeleteItemReq req) {
        ItemEntity item = itemMapper.map(req, ItemEntity.class);
        repository.delete(item);
        return new DeleteItemRes();
    }*/

}