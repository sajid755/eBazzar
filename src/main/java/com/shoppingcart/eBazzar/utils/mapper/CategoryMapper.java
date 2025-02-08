package com.shoppingcart.eBazzar.utils.mapper;

import com.shoppingcart.eBazzar.dto.CategoryDto;
import com.shoppingcart.eBazzar.model.Category;
import org.mapstruct.factory.Mappers;

public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category categoryDtoToCategory(CategoryDto categoryDto);
}
