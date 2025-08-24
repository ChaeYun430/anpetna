package com.anpetna.item;

import com.anpetna.coreDto.ImageDTO;
import com.anpetna.item.dto.deleteReview.DeleteReviewReq;
import com.anpetna.item.dto.deleteReview.DeleteReviewRes;
import com.anpetna.item.dto.modifyReview.ModifyReviewReq;
import com.anpetna.item.dto.modifyReview.ModifyReviewRes;
import com.anpetna.item.dto.registerReview.RegisterReviewReq;
import com.anpetna.item.dto.registerReview.RegisterReviewRes;
import com.anpetna.item.dto.searchOneReview.SearchOneReviewReq;
import com.anpetna.item.dto.searchOneReview.SearchOneReviewRes;
import com.anpetna.item.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Log4j2
@SpringBootTest
@ActiveProfiles("item")
public class ReviewServiceTests {

    @Autowired
    ReviewService reviewService;

    @Test
    public void registerReview() {
        //given
        ImageDTO image1 = ImageDTO.builder()
                .fileName("리뷰용 이미지1")
                .url("https://www.baidu.com")
                .sortOrder(1)
                .build();
        ImageDTO image2 = ImageDTO.builder()
                .fileName("리뷰용 이미지2")
                .url("https://www.baidu.com")
                .sortOrder(2)
                .build();
        RegisterReviewReq req = RegisterReviewReq.builder()
                .content("리뷰 내용")
                .itemId(1L)
                .rating(1)
                .build();
        req.addImage(image1);
        req.addImage(image2);

        //when
        RegisterReviewRes res = reviewService.registerReview(req);

        //then
        log.info(res);
    }

    @Test
    public void modifyReview(){
        //  item은 건드릴 필요없음
        //  리뷰 수정은 아이디로 조회 후 내용, 별점, 이미지 수정 가능
        //given
        ImageDTO image1 = ImageDTO.builder()
                .fileName("수정한 이미지 파일1")
                .url("https://www.baidu.com11111111")
                .sortOrder(1)
                .build();
        ImageDTO image2 = ImageDTO.builder()
                .fileName("수정한 이미지 파일2")
                .url("https://www.baidu.com222222222")
                .sortOrder(1)
                .build();
        ModifyReviewReq req = ModifyReviewReq.builder()
                .content("수정된 내용")
                .rating(5)
                .reviewId(7L)
                .build();
        req.addImage(image1);
        req.addImage(image2);

        //when
        ModifyReviewRes res = reviewService.modifyReview(req);

        //then
        log.info(res);
    }
/*    @Test
    @Transactional
    public void searchOneReview() {
        SearchOneReviewReq req = new SearchOneReviewReq();
        req.setReviewId(1L);
        SearchOneReviewRes res = reviewService.getOneReview(req);
        System.out.println(res);
    }*/

/*    @Test
    public void searchAllItem() {

        SearchAllItemsReq req = new SearchAllItemsReq();

        req.setSortBySale(ItemSellStatus.SOLD_OUT);
        List<ItemDTO> res1 = reviewService.getAllReviews(req);
        System.out.println(res1);

        req.setSortByCategory(ItemCategory.BATH_PRODUCT);
        List<ItemDTO> res2 = reviewService.getAllItems(req);
        System.out.println(res2);

        req.setOrderByPriceDir(SortDirection.ASCENDING);
        List<ItemDTO> res3 = reviewService.getAllItems(req);
        System.out.println(res3);

    }*/



    @Test
    public void deleteItem(){
        DeleteReviewReq req = new DeleteReviewReq();
        req.setReviewId(1L);
        DeleteReviewRes res = reviewService.deleteReview(req);
        System.out.println(res);
    }
}
