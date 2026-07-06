package com.Ecommerce.Ecommerce.Repository;

import com.Ecommerce.Ecommerce.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
