package com.shoppingcart.eBazzar.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDto {
    private Long itemId;
    private int quantity;
    private BigDecimal unitPrice = BigDecimal.ZERO;
    private BigDecimal totalPrice;
    private ProductDto product;

}
