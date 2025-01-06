package com.shoppingcart.eBazzar.service.order;

import java.util.List;

import com.shoppingcart.eBazzar.dto.OrderDto;

public interface IOrderService {

    OrderDto placeOrder(Long userId);

    OrderDto getOrder(Long id);

    List<OrderDto> getUserOrders(Long userId);

}
