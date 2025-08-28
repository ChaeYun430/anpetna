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
import com.anpetna.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/items")
@Log4j2
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    //@PreAuthorize("hasRole('USER')")
    //  컨트롤러나 서비스 메서드 실행 전에 SpEL(Security Expression Language)로 권한 검증
    public ResponseEntity<RegisterItemRes> registerItem(@RequestPart RegisterItemReq postReq, @RequestPart List<MultipartFile> files) {
        var postRes = itemService.registerItem(postReq, files);
        return ResponseEntity.ok(postRes);
    }

    @PutMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModifyItemRes> updateItem(@RequestPart ModifyItemReq putReq, @RequestPart List<MultipartFile> files) {
        var putRes = itemService.modifyItem(putReq, files);
        return ResponseEntity.ok(putRes);
    }

    @DeleteMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteItemRes> deleteItem(@RequestBody DeleteItemReq deleteReq) {
        var deleteRes = itemService.deleteItem(deleteReq);
        return ResponseEntity.ok(deleteRes);
    }


    @GetMapping("/{ItemId}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SearchOneItemRes> searchOneItem(@RequestBody SearchOneItemReq req) {
        var getOneResult = itemService.getOneItem(req);
        return ResponseEntity.ok(getOneResult);
    }

    @GetMapping("/{sortItem}")
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<ItemDTO>> searchAllItems(@RequestBody SearchAllItemsReq req) {
        var getAllResult = itemService.getAllItems(req);
        return ResponseEntity.ok(getAllResult);
    }

    // (@RequestBody RegisterItemReq req)
    // Content-Type: application/json
    // 요청 바디 전체가 JSON이어야 함
    // 파일 업로드 불가

    // (@RequestParam("file") MultipartFile file)
    // multipart/form-data에서 단일 필드 처리
    // 단일 파일이나 간단한 문자열 처리 가능
    // JSON과 함께 보내기 어렵다

    // @RequestPart("item") RegisterItemReq req, @RequestPart(value="images", required=false) List<MultipartFile> files)
    // multipart/form-data 처리용
    // JSON 객체(item) + 파일(images) 동시 전송 가능
    // Postman form-data에서 Text(JSON) + File 같이 보낼 수 있음


    //  @PreAuthorize("#id == principal.id")            // 요청 파라미터 id와 로그인 사용자 id 같을 때만 허용
    //  @PreAuthorize("isAuthenticated()")              // 로그인만 되어 있으면 허용
    //  @PreAuthorize("permitAll()")                    // 모두 허용
    //판매량순, 가격순
    //soldout처리
    //onsale여부

}
