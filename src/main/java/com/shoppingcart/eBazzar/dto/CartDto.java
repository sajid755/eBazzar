package com.shoppingcart.eBazzar.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.shoppingcart.eBazzar.model.CartItem;

import lombok.Data;

@Data
public class CartDto {
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Set<CartItem> items = new HashSet<>();

}
