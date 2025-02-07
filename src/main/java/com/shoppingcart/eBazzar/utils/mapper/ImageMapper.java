package com.shoppingcart.eBazzar.utils.mapper;

import com.shoppingcart.eBazzar.dto.ImageDto;
import com.shoppingcart.eBazzar.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageDto imageToImageDto(Image image);
}
