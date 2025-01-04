package com.shoppingcart.eBazzar.service.order;

import com.shoppingcart.eBazzar.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);

    Order getOrder(Long id);

}
