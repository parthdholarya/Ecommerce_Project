package com.Ecommerce.Ecommerce.Repository;

import com.Ecommerce.Ecommerce.Model.Order;
import com.Ecommerce.Ecommerce.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}