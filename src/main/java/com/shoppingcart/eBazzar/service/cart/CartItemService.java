package com.shoppingcart.eBazzar.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.shoppingcart.eBazzar.Repository.CartItemRepository;
import com.shoppingcart.eBazzar.Repository.CartRepository;
import com.shoppingcart.eBazzar.exception.ResourceNotFoundException;
import com.shoppingcart.eBazzar.model.Cart;
import com.shoppingcart.eBazzar.model.CartItem;
import com.shoppingcart.eBazzar.model.Product;
import com.shoppingcart.eBazzar.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // Fetch the cart and product
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        // Find the cart item or create a new one
        CartItem cartItem = null;
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                cartItem = item;
                break;
            }
        }

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUnitPrice(product.getPrice());

        }

        // Update the quantity and total price
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        // Save changes
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);

        // Update the quantity and prices of the matching item
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                item.setUnitPrice(item.getProduct().getPrice());
                item.setTotalPrice();
                break;
            }
        }

        // Calculate the total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            totalAmount = totalAmount.add(item.getTotalPrice());
        }

        // Update and save the cart
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem foundItem = null;

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                foundItem = item;
                break;
            }
        }

        if (foundItem == null) {
            throw new ResourceNotFoundException("Item not found");
        }

        return foundItem;
    }

}
