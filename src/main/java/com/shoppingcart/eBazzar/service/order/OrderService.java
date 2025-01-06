package com.shoppingcart.eBazzar.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shoppingcart.eBazzar.dto.OrderDto;

import com.shoppingcart.eBazzar.Repository.OrderRepository;
import com.shoppingcart.eBazzar.Repository.ProductRepository;
import com.shoppingcart.eBazzar.enums.OrderStatus;
import com.shoppingcart.eBazzar.exception.ResourceNotFoundException;
import com.shoppingcart.eBazzar.model.Cart;
import com.shoppingcart.eBazzar.model.CartItem;
import com.shoppingcart.eBazzar.model.Order;
import com.shoppingcart.eBazzar.model.OrderItem;
import com.shoppingcart.eBazzar.model.Product;
import com.shoppingcart.eBazzar.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return convertToDto(savedOrder);

    }

    @Override
    public OrderDto getOrder(Long id) {
        return orderRepository.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found!"));
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItem item : orderItemList) {
            BigDecimal itemTotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
        }

        return totalPrice;
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
