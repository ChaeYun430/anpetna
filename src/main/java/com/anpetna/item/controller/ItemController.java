package com.anpetna.item.controller;


import com.anpetna.item.dto.ItemDTO;
import com.anpetna.item.dto.DeleteItemReq;
import com.anpetna.item.dto.ModifyItemReq;
import com.anpetna.item.dto.RegisterItemReq;
import com.anpetna.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
@Log4j2
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDTO> registerItem(@RequestBody RegisterItemReq registerItemReq) {

        var postResult = itemService.registerItem(registerItemReq);

        return new ResponseEntity<>(postResult, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ItemDTO> updateItem(@RequestBody ModifyItemReq modifyItemReq) {

        var putResult = itemService.modifyItem(modifyItemReq);

        return new ResponseEntity<>(putResult, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ItemDTO> deleteItem(@RequestBody DeleteItemReq deleteItemReq) {

        var deleteResult = itemService.deleteItem(deleteItemReq);

        return new ResponseEntity<>(deleteResult, HttpStatus.OK);
    }


    //Get

    @GetMapping("/{ItemId}")
    public ResponseEntity<ItemDTO> searchOneItem(@PathVariable Long itemId) {

        var getOneResult = itemService.getOneItem(itemId);

        return new ResponseEntity<>(getOneResult, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> searchAllItems() {

        var getAllResult = itemService.getAllItems();

        return new ResponseEntity<>(getAllResult, HttpStatus.OK);
    }
/*
    @GetMapping("/{ItemId}")
    public ResponseEntity<ItemDTO> sortByCategory(@PathVariable Long itemId) {

        var sortByCategory = itemService.getOneItem(itemId);

        return new ResponseEntity<>(sortByCategory, HttpStatus.OK);
    }

    @GetMapping("/{ItemId}")
    public ResponseEntity<ItemDTO> OrderBy(@PathVariable Long itemId) {

        var OrderBy = itemService.getOneItem(itemId);

        return new ResponseEntity<>(OrderBy, HttpStatus.OK);
    }*/

    //판매량순, 가격순
    //soldout처리
    //onsale여부



}
