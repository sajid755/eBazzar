package com.shoppingcart.eBazzar.service.cart;

import java.math.BigDecimal;

import com.shoppingcart.eBazzar.model.Cart;

public interface ICartService {

    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

}
