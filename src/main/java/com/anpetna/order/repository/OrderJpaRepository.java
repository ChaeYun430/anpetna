package com.anpetna.order.repository;

import com.anpetna.order.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {


}
