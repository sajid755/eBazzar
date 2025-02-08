package com.shoppingcart.eBazzar.service.user;

import com.shoppingcart.eBazzar.Repository.UserRepository;
import com.shoppingcart.eBazzar.dto.requests.CreateUserRequest;
import com.shoppingcart.eBazzar.dto.requests.UserUpdateRequest;
import com.shoppingcart.eBazzar.exception.AlreadyExistsException;
import com.shoppingcart.eBazzar.exception.ResourceNotFoundException;
import com.shoppingcart.eBazzar.model.User;
import com.shoppingcart.eBazzar.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User createUser(CreateUserRequest request) throws AlreadyExistsException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Oops! " + request.getEmail() + " already exists!");
        }

        User user = UserMapper.INSTANCE.createUserRequestToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) throws ResourceNotFoundException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User updatedUser = getUpdatedUser(existingUser, request);

        return userRepository.save(updatedUser);
    }

    private User getUpdatedUser(User existingUser, UserUpdateRequest request) {
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        return existingUser;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id " + userId + " not found"));
        userRepository.delete(user);
    }
}
