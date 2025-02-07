package com.shoppingcart.eBazzar.utils.mapper;

import com.shoppingcart.eBazzar.dto.CartItemDto;
import com.shoppingcart.eBazzar.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartItemMapper {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    CartItemDto cartItemToCartItemDto(Cart cart);
}
