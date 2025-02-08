package com.shoppingcart.eBazzar.dto.requests;

import com.shoppingcart.eBazzar.dto.CategoryDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequestDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private CategoryDto category;
}
