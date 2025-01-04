package com.shoppingcart.eBazzar.service.order;

import java.util.List;

import com.shoppingcart.eBazzar.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);

    Order getOrder(Long id);

    List<Order> getUserOrders(Long userId);

}
