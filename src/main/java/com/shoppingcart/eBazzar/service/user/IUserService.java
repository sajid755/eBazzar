package com.shoppingcart.eBazzar.service.user;

import com.shoppingcart.eBazzar.dto.UserDto;
import com.shoppingcart.eBazzar.model.User;
import com.shoppingcart.eBazzar.requests.CreateUserRequest;
import com.shoppingcart.eBazzar.requests.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    // UserDto convertUserToDto(User user);

}
