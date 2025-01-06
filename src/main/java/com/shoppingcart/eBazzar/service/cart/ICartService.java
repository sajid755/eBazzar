package com.shoppingcart.eBazzar.service.cart;

import java.math.BigDecimal;

import com.shoppingcart.eBazzar.model.Cart;
import com.shoppingcart.eBazzar.model.User;

public interface ICartService {

    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);

}
