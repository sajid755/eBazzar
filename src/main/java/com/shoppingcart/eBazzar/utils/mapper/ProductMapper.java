package com.shoppingcart.eBazzar.utils.mapper;

import com.shoppingcart.eBazzar.dto.ProductDto;
import com.shoppingcart.eBazzar.dto.requests.AddProductRequestDto;
import com.shoppingcart.eBazzar.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {
        CategoryMapper.class,
        ImageMapper.class
})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto productToProductDto(Product product);

    Product AddProductRequestDtoToProduct(AddProductRequestDto addProductRequestDto);
}
