package com.anpetna.item.controller;

import com.anpetna.ApiResult;
import com.anpetna.core.coreDto.PageResponseDTO;
import com.anpetna.item.dto.ItemDTO;
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
import com.anpetna.item.service.ItemService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/item")
@Log4j2
@RequiredArgsConstructor
public class ItemController {
    //  컨트롤러나 서비스 메서드 실행 전에 SpEL(Security Expression Language)로 권한 검증

    //=============================점검 요소================================
    // item C R1 U D RALL 완
    // 정렬 조건 검토
    // 이미지 수정시 삭제와 입력의 과정
    // 불필요한 어노테이션 정리
    // 프론트에서 구현시 주의사항 정리 및 전달
    // CONTENTTYPE 점검
    // json만 /json & file -> 상품에는 필요없으나 리뷰는 선택 가능하도록
    // 삭제한 상품에 대한 응답처리
    //일반 쇼핑몰/커머스에서는 상품에 종속된 구조(/items/{itemId}/images)가 RESTful
    //=====================================================================

    private final ItemService itemService;

    //thumb는 필수 입력
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<RegisterItemRes> registerItem(@RequestPart RegisterItemReq postReq,
                                                   @RequestPart MultipartFile thumb,
                                                   @RequestPart List<MultipartFile> files) throws IOException {
        var postRes = itemService.registerItem(postReq, thumb, files);
        return new ApiResult<>(postRes);
    }

    //URL → 자원 식별, Body → 수정할 필드들로 역할을 분리
    //삭제할 이미지는 reqDTO에 List<UUID>로 담음
    @PutMapping(value = "/{itemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<ModifyItemRes> updateItem(@PathVariable Long itemId,
                                               @RequestPart ModifyItemReq putReq,
                                               @RequestPart MultipartFile thumb,
                                               @RequestPart List<MultipartFile> files) {
        putReq.setItemId(itemId);
        var putRes = itemService.modifyItem(putReq,thumb,files);
        return new ApiResult<>(putRes);
    }

    @DeleteMapping("/{itemId}")
    public ApiResult<DeleteItemRes> deleteItem(@PathVariable Long itemId) {
        DeleteItemReq deleteReq = DeleteItemReq.builder()
                .itemId(itemId)
                .build();
        var deleteRes = itemService.deleteItem(deleteReq);
        return new ApiResult<>(deleteRes);
    }

    @GetMapping(value = "/{itemId}") //상품 수정 화면용 데이터 조회시 필요 (기존 정보 채우기)
    public ApiResult<SearchOneItemRes> searchOneItem(@PathVariable Long itemId) {
        SearchOneItemReq getOneReq = new SearchOneItemReq();
        getOneReq.setItemId(itemId);
        var getOneRes = itemService.getOneItem(getOneReq);
        return new ApiResult<>(getOneRes);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<PageResponseDTO<SearchAllItemsRes>> searchAllItems(@RequestBody SearchAllItemsReq getReq) {
        var getAllResult = itemService.getAllItems(getReq);
        return new ApiResult<>(getAllResult);
    }

    //REST 철학 : 리소스를 명확하게 표현하고, 동사는 HTTP 메서드로 해결
    //순수 백엔드 설계 + 다양한 클라이언트 (웹, 앱) 를 고려한다면 RESTful이 맞고,
    //웹 프론트 React/Vue 전용 백엔드라면 Pragmatic이 더 실용적

    //통계
    //옵션 처리


    //클라이언트는 JSON 받아서 리스트 렌더링
    //이미지 <img src="{thumbnailUrl}">로 lazy load 가능

//============================================ 이론 정리 ==========================================
    //PathVariable : 자원을 식별할 때 적합 / RESTful 스타일에서 자주 씀.
    //RequestParam : 검색, 필터링, 옵션 같은 부가 조건에 적합.

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

    //@RequestPart를 쓰면 Postman에서 반드시 form-data로 보내야 하고,
    //DTO는 JSON 문자열(Text), 파일은 File 타입으로 넣어야 매핑됩니다.
//============================================ 이론 정리 ==========================================

}
