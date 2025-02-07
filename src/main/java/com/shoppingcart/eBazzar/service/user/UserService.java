package com.shoppingcart.eBazzar.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shoppingcart.eBazzar.Repository.UserRepository;
import com.shoppingcart.eBazzar.dto.UserDto;
import com.shoppingcart.eBazzar.exception.AlreadyExistsException;
import com.shoppingcart.eBazzar.exception.ResourceNotFoundException;
import com.shoppingcart.eBazzar.model.User;
import com.shoppingcart.eBazzar.dto.requests.CreateUserRequest;
import com.shoppingcart.eBazzar.dto.requests.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Oops! " + request.getEmail() + " already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) throws ResourceNotFoundException, RuntimeException{
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete,
                () -> new ResourceNotFoundException("User Not Found!"));
    }

    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
