package com.anpetna.item.service;

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
import com.anpetna.item.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ModelMapper modelMapper;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    @Override
    public RegisterItemRes registerItem(RegisterItemReq req) {
        ItemEntity item = itemMapper.cItemMapReq().map(req);
        ItemEntity savedItem = itemRepository.save(item);
        //savedItem.getImages().forEach(m->System.out.println(m.getItem()));
        RegisterItemRes res = modelMapper.map(savedItem, RegisterItemRes.class);
        return  res.registered();
    }

    @Override
    public ModifyItemRes modifyItem(ModifyItemReq req) {
        ItemEntity foundModified = itemMapper.uItemMapReq().map(req);
        ItemEntity saved = itemRepository.save(foundModified);
        ModifyItemRes res = modelMapper.map(saved, ModifyItemRes.class);
        return res.modified();
    }

    @Override
    public DeleteItemRes deleteItem(DeleteItemReq req) {
        itemRepository.deleteById(req.getItemId());
        DeleteItemRes res = DeleteItemRes.builder()
                .itemId(req.getItemId())
                .itemName(req.getItemName())
                .build();
        return res.deleted();
    }

    @Override
    public SearchOneItemRes getOneItem(SearchOneItemReq req) {
        Optional<ItemEntity> found = itemRepository.findById(req.getItemId());
        ItemEntity res = found.orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + req.getItemId()));
        return itemMapper.r1ItemMapRes().map(res);
    }

     @Override
    public List<ItemDTO> getAllItems(SearchAllItemsReq req) {

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), req.getSort());
        Page<ItemEntity> getAllItems = itemRepository.findAll(pageable);


        List<ItemEntity> found = null;
        //  사용자는 셋 중 하나를 선택하고 DTO에는 값이 하나만 지정된다.
        if (req.getSortByCategory() != null){
            found = itemRepository.sortByCategory(req);
        }else if (req.getSortBySale() != null){
            found = itemRepository.sortBySales(req);
        }else if (req.getOrderByPriceDir() != null){
            found  = itemRepository.orderByPriceDir(req);
        }else{

        }

        List<ItemDTO> res  = new ArrayList<>();
        found.forEach(itemEntity -> {
             res.add(itemMapper.rItemMapRes().map(itemEntity));
         });

        return res;
    }
}