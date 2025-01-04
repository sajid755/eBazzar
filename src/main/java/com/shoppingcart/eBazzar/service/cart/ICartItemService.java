package com.shoppingcart.eBazzar.service.cart;

import com.shoppingcart.eBazzar.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long ProductId);

    void updateItemQuantity(Long cartId, Long ProductId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);

}
