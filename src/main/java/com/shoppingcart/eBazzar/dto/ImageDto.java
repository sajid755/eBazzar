package com.shoppingcart.eBazzar.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String fileName;
    private String downloadUrl;

    // No-argument constructor (required by ModelMapper)
    public ImageDto() {
    }

    // Parameterized constructor
    public ImageDto(Long id, String fileName, String downloadUrl) {
        this.id = id;
        this.fileName = fileName;
        this.downloadUrl = downloadUrl;
    }
}
