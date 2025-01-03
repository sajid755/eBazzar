package com.shoppingcart.eBazzar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.eBazzar.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
