package com.shoppingcart.eBazzar.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> order;
    private CartDto cart;
}
