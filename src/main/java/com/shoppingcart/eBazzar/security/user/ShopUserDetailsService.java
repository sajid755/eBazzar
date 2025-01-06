package com.shoppingcart.eBazzar.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoppingcart.eBazzar.Repository.UserRepository;
import com.shoppingcart.eBazzar.exception.ResourceNotFoundException;
import com.shoppingcart.eBazzar.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new ResourceNotFoundException(email + "doesn't exits"));

        return ShopUserDetails.createUserDetails(user);
    }

}
