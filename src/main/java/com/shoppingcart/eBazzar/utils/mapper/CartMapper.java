package com.shoppingcart.eBazzar.utils.mapper;

import com.shoppingcart.eBazzar.dto.CartDto;
import com.shoppingcart.eBazzar.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDto cartToCartDto(Cart cart);
}
