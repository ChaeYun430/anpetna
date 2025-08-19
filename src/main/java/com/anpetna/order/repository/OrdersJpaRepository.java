package com.anpetna.order.repository;

import com.anpetna.order.domain.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersJpaRepository extends JpaRepository<OrdersEntity, Long> {



}
