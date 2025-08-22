package com.anpetna.item.controller;

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
import com.anpetna.item.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    //컨트롤러나 서비스 메서드 실행 전에 SpEL(Security Expression Language)로 권한 검증
    public ResponseEntity<RegisterItemRes> registerItem(@RequestBody RegisterItemReq registerItemReq) {
        var postResult = reviewService.registerReview(registerItemReq);
        return new ResponseEntity<>(postResult, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModifyItemRes> updateItem(@RequestBody ModifyItemReq modifyItemReq) {
        var putResult = reviewService.modifyReview(modifyItemReq);
        return new ResponseEntity<>(putResult, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteItemRes> deleteItem(@RequestBody DeleteItemReq deleteItemReq) {
        var deleteResult = reviewService.deleteReview(deleteItemReq);
        return new ResponseEntity<>(deleteResult, HttpStatus.OK);
    }


    @GetMapping("/{ItemId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SearchOneItemRes> searchOneItem(@RequestBody SearchOneItemReq req) {
        var getOneResult = reviewService.getOneReview(req);
        return new ResponseEntity<>(getOneResult, HttpStatus.OK);
    }

    @GetMapping("/{sortItem}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ItemDTO>> searchAllItems(@RequestBody SearchAllItemsReq req) {
        var getAllResult = reviewService.getAllReviews(req);
        return new ResponseEntity<>(getAllResult, HttpStatus.OK);
    }

}
