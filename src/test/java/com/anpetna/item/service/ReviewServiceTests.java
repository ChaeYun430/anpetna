package com.anpetna.item.service;

import com.anpetna.coreDto.ImageDTO;
import com.anpetna.item.constant.ItemCategory;
import com.anpetna.item.constant.ItemSaleStatus;
import com.anpetna.item.constant.ItemSellStatus;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReviewServiceTests {

    @Autowired
    ItemService itemService;

    @Test
    public void registerItemTest() {
        ImageDTO image1 = ImageDTO.builder()
                .fileName("이미지파일1")
                .url("https://www.baidu.com")
                .sortOrder(1)
                .build();
        ImageDTO image2 = ImageDTO.builder()
                .fileName("이미지파일2")
                .url("https://www.baidu.com")
                .sortOrder(1)
                .build();
        RegisterItemReq req = RegisterItemReq.builder() //id는 자동생성 그렇다면 dto처리는??
                .itemName("test")
                .itemPrice(100)
                .itemStock(200)
                .itemDetail("test")
                .itemCategory(ItemCategory.TOY)
                .itemSellStatus(ItemSellStatus.SELL)
                .itemSaleStatus(ItemSaleStatus.ONSALE)
                .itemImages(List.of(image1, image2))
                .build();
        itemService.registerItem(req);




    }


}
