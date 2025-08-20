package com.anpetna.item.service;

import com.anpetna.coreDomain.ImageEntity;
import com.anpetna.item.config.ItemMapper;
import com.anpetna.item.domain.ItemEntity;
import com.anpetna.item.dto.ItemDTO;
import com.anpetna.item.dto.deleteItem.DeleteItemReq;
import com.anpetna.item.dto.deleteItem.DeleteItemRes;
import com.anpetna.item.dto.modifyItem.ModifyItemReq;
import com.anpetna.item.dto.modifyItem.ModifyItemRes;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import com.anpetna.item.dto.registerItem.RegisterItemRes;
import com.anpetna.item.dto.searchAllItem.SearchAllItemsReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemReq;
import com.anpetna.item.dto.searchOneItem.SearchOneItemRes;
import com.anpetna.item.repository.ItemJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.anpetna.coreDomain.QImageEntity.imageEntity;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemJpaRepository repository;
    private ModelMapper modelMapper = new ModelMapper();
    private ItemMapper itemMapper = new ItemMapper();

     @Override
    public List<ItemDTO> getAllItems(SearchAllItemsReq req) {
         List<ItemEntity> found = repository.findAll();
        if (req.getSortByCategory() != null){
            found = repository.sortByCategory(req);
        }else if (req.getSortBySale() != null){
            found = repository.orderBySales(req);
        }else if (req.getSortByPrice() != null){
            found  = repository.orderByPrice(req);
        }
         List<ItemDTO> res = found.stream()
                 .map(entity -> modelMapper.map(entity, ItemDTO.class))
                 .collect(Collectors.toList());
        return res;
    }

    @Override
    public SearchOneItemRes getOneItem(SearchOneItemReq req) {
        Optional<ItemEntity> found = repository.findById(req.getItemId());
        ItemEntity res = found.orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + req.getItemId()));
       return itemMapper.r1ItemMapRes().map(res);
    }

    @Override
    public RegisterItemRes registerItem(RegisterItemReq req) {
        ItemEntity item = itemMapper.cItemMapReq().map(req);
        ItemEntity savedItem = repository.save(item);
        RegisterItemRes res = itemMapper.cItemMapRes().map(savedItem);
        res.setRes("registered");
        return res;
    }// 이미지에 아이디가 안 들어간다.

    @Override
    public ModifyItemRes modifyItem(ModifyItemReq req) {
        ItemEntity found = repository.findById(req.getItemId()).orElse(null);
        modelMapper.map(req, found);
        ItemEntity saved = repository.save(found);
        ModifyItemRes res = modelMapper.map(found, ModifyItemRes.class);
        res.setRes("modified");
        return res;
    }

    @Override
    public DeleteItemRes deleteItem(DeleteItemReq req) {
        repository.deleteById(req.getItemId());
        DeleteItemRes res = DeleteItemRes.builder()
                .itemId(req.getItemId())
                .itemName(req.getItemName())
                .res("deleted")
                .build();
        return res;
    }
}