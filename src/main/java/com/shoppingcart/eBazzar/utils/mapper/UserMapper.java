package com.shoppingcart.eBazzar.utils.mapper;


import com.shoppingcart.eBazzar.dto.UserDto;
import com.shoppingcart.eBazzar.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);
}
