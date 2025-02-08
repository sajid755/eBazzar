package com.shoppingcart.eBazzar.utils.mapper;


import com.shoppingcart.eBazzar.dto.OrderDto;
import com.shoppingcart.eBazzar.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto orderToOrderDto(Order order);
}
