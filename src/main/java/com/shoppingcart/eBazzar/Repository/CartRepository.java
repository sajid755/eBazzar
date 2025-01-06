package com.shoppingcart.eBazzar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingcart.eBazzar.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findCartByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id = :id")
    void deleteCartById(@Param("id") Long id);

}
