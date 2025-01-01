package com.shoppingcart.eBazzar.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String fileName;
    private String downloadUrl;

    public ImageDto(Long id, String fileName, String downloadUrl) {
    }
}
