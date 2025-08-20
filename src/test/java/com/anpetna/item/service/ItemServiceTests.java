package com.anpetna.item.service;

import com.anpetna.coreDto.ImageDTO;
import com.anpetna.item.constant.ItemCategory;
import com.anpetna.item.constant.ItemSaleStatus;
import com.anpetna.item.constant.ItemSellStatus;
import com.anpetna.item.dto.registerItem.RegisterItemReq;
import com.anpetna.item.dto.registerItem.RegisterItemRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@EnableJpaAuditing
public class ItemServiceTests {

    @Autowired
    ItemService itemService;

    @Test
    public void registerItem() {
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
        RegisterItemRes res = itemService.registerItem(req);

        System.out.println(res);
    }
}
