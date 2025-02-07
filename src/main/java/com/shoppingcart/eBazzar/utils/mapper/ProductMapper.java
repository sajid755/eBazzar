package com.shoppingcart.eBazzar.utils.mapper;

import com.shoppingcart.eBazzar.dto.ProductDto;
import com.shoppingcart.eBazzar.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto productToProductDto(Product product);
}
