package com.shoppingcart.eBazzar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.eBazzar.requests.LoginRequest;
import com.shoppingcart.eBazzar.response.ApiResponse;
import com.shoppingcart.eBazzar.response.JwtResponse;
import com.shoppingcart.eBazzar.security.jwt.JwtUtils;
import com.shoppingcart.eBazzar.security.user.ShopUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = jwtUtils.GenerateTokenForUser(authentication);

            // Get the user details from the authentication object
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();

            // Prepare the response
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

            // Return the success response with JWT
            return ResponseEntity.ok(new ApiResponse("Login Successful", jwtResponse));

        } catch (AuthenticationException e) {
            // Log failed login attempt
            // logger.warn("Failed login attempt for user: {}", request.getEmail());

            // Return Unauthorized response with a generic message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid credentials", null));
        }
    }

}
