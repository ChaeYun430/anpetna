package com.anpetna;

import com.anpetna.item.constant.ItemCategory;
import com.anpetna.item.constant.ItemSellStatus;
import com.anpetna.item.domain.ItemEntity;
import com.anpetna.order.domain.OrderEntity;
import com.anpetna.order.domain.OrdersEntity;
import com.anpetna.order.dto.OrdersDTO;
import com.anpetna.order.repository.OrderRepository;
import com.anpetna.order.repository.OrdersRepository;
import com.anpetna.order.service.OrdersService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;



@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@org.springframework.test.context.jdbc.Sql(statements = {
        "ALTER TABLE anpetna_item MODIFY item_thumbs_id VARCHAR(255) NOT NULL DEFAULT 'thumbs-test'"
}, executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Log4j2
public class OrdersServiceTest {

    @Autowired
    OrdersService ordersService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderRepository orderRepository;

    @PersistenceContext
    EntityManager em;

    // ===== 테스트 데이터 생성 유틸 =====
    private ItemEntity newItem(String name, int price) {
        ItemEntity item = ItemEntity.builder()
                .itemName(name)
                .itemPrice(price)
                .itemStock(1000)
                .itemDetail("상세-" + name)
                .itemSellStatus(ItemSellStatus.SELL)
                .itemCategory(ItemCategory.TOY)
                // MariaDB 스키마에 item_thumbs_id NOT NULL이면 필드 세팅 필요
                // 엔티티에 필드가 있다면 아래 한 줄 활성화하세요:
                // .itemThumbsId("thumbs-" + name)
                .build();

        log.info("============================================================");
        log.info("newItem : " + item);
        log.info("============================================================");

        em.persist(item);
        return item;
    }

    private OrdersEntity newOrders(String memberId) {
        var orders = OrdersEntity.builder()
                .memberId(memberId)
                .cardId("CARD-" + memberId)
                .totalAmount(0)
                .build();

        log.info("============================================================");
        log.info("newOrders : " + orders);
        log.info("============================================================");

        return ordersRepository.save(orders);
    }

    private OrderEntity addOrderItem(OrdersEntity orders, ItemEntity item, int price, int qty) {
        OrderEntity orderItem = OrderEntity.builder()
                .itemEntity(item)
                .price(price)
                .quantity(qty)
                .build();

        // 양방향 편의 메서드로 연관관계 주입(메모리 컬렉션도 채워짐)
        orders.addOrderItem(orderItem);

        log.info("============================================================");
        log.info("addOrderItem : " + orderItem);
        log.info("============================================================");

        return orderRepository.save(orderItem);


    }

    // ===== 테스트 시작 =====

    @Test
    @DisplayName("getDetail: 주문 상세 DTO 반환 및 수량 합계 계산")
    @Commit
    void getDetail_ok() {
        // given
        var item1 = newItem("장난감A", 7000);
        var item2 = newItem("장난감B", 8000);
        var orders = newOrders("user-a");
        addOrderItem(orders, item1, 7000, 2); // 합계 수량 2
        addOrderItem(orders, item2, 8000, 3); // 합계 수량 3 → 총 5

        // when
        OrdersDTO dto = ordersService.getDetail(orders.getOrdersId());

        log.info("============================================================");
        log.info("getDetail_ok : " + orders);
        log.info("getDetail_ok : " + item1);
        log.info("getDetail_ok : " + item2);
        log.info("getDetail_ok : " + dto);
        log.info("============================================================");

        // then
        assertThat(dto.getOrdersId()).isEqualTo(orders.getOrdersId());
        assertThat(dto.getMemberId()).isEqualTo("user-a");
        assertThat(dto.getCardId()).isEqualTo("CARD-user-a");
        assertThat(dto.getItemQuantity()).isEqualTo(5); // 2 + 3
    }

    @Test
    @DisplayName("getDetail: 주문 없으면 IllegalArgumentException")
    // @Commit
    void getDetail_notFound() {
        // given
        Long notExistId = -999L;

        // then
        assertThatThrownBy(() -> ordersService.getDetail(notExistId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("주문을 찾을 수 없습니다");
    }

    @Test
    @DisplayName("getSummary: 주문 요약 반환 (getDetail과 동일 매핑)")
    @Commit
    void getSummary_ok() {
        // given
        var item = newItem("장난감C", 9000);
        var orders = newOrders("user-b");
        addOrderItem(orders, item, 9000, 1);

        log.info("============================================================");
        log.info("getDetail_ok : " + item);
        log.info("getDetail_ok : " + orders);
        log.info("============================================================");

        // when
        OrdersDTO dto = ordersService.getSummary(orders.getOrdersId());

        log.info("============================================================");
        log.info("getDetail_ok : " + dto);
        log.info("============================================================");

        // then
        assertThat(dto.getOrdersId()).isEqualTo(orders.getOrdersId());
        assertThat(dto.getMemberId()).isEqualTo("user-b");
        assertThat(dto.getItemQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("getSummariesByMember: memberId로 페이지 조회")
    // @Commit
    void getSummariesByMember_ok() {
        String member = "user-c-" + java.util.UUID.randomUUID(); // 고유 memberId

        var o1 = newOrders(member);
        var o2 = newOrders(member);
        var oX = newOrders("other");

        var item = newItem("장난감D", 5000);
        addOrderItem(o1, item, 5000, 1);
        addOrderItem(o2, item, 5000, 2);
        addOrderItem(oX, item, 5000, 3);

        log.info("============================================================");
        log.info("getSummariesByMember_ok : " + o1);
        log.info("getSummariesByMember_ok : " + o2);
        log.info("getSummariesByMember_ok : " + oX);
        log.info("getSummariesByMember_ok : " + item);
        log.info("============================================================");

        // when
        var page = ordersService.getSummariesByMember(member, PageRequest.of(0, 10));

        log.info("============================================================");
        log.info("getSummariesByMember_ok : " + page);
        log.info("============================================================");

        // then
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent())
                .extracting(OrdersDTO::getOrdersId)
                .containsExactlyInAnyOrder(o1.getOrdersId(), o2.getOrdersId());
    }

    @Test
    @DisplayName("delete: 하위 품목 삭제 후 헤더 삭제")
    @Commit
    void delete_ok() {
        // given
        var item = newItem("장난감E", 6000);
        var orders = newOrders("user-d");
        var child1 = addOrderItem(orders, item, 6000, 1);
        var child2 = addOrderItem(orders, item, 6000, 4);

        log.info("============================================================");
        log.info("delete_ok : " + item);
        log.info("delete_ok : " + orders);
        log.info("delete_ok : " + child1);
        log.info("delete_ok : " + child2);
        log.info("============================================================");


        // sanity check
        var result = assertThat(orderRepository.findByOrders_OrdersId(orders.getOrdersId())).hasSize(2);

        log.info("============================================================");
        log.info(result);
        log.info("============================================================");

        // when
        ordersService.delete(orders.getOrdersId());

        // then
        assertThat(ordersRepository.findById(orders.getOrdersId())).isEmpty();
        assertThat(orderRepository.findByOrders_OrdersId(orders.getOrdersId())).isEmpty();
    }

}