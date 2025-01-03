package com.shoppingcart.eBazzar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.eBazzar.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllByCartId(Long id);

}
