package com.shoppingcart.eBazzar.service.order;

import java.util.List;

import com.shoppingcart.eBazzar.dto.OrderDto;
import com.shoppingcart.eBazzar.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);

    OrderDto getOrder(Long id);

    List<OrderDto> getUserOrders(Long userId);

}
