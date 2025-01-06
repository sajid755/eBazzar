package com.shoppingcart.eBazzar.service.cart;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shoppingcart.eBazzar.Repository.CartItemRepository;
import com.shoppingcart.eBazzar.Repository.CartRepository;
import com.shoppingcart.eBazzar.exception.ResourceNotFoundException;
import com.shoppingcart.eBazzar.model.Cart;
import com.shoppingcart.eBazzar.model.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId);
    }

}
