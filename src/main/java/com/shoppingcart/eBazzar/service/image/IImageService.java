package com.shoppingcart.eBazzar.service.image;

import com.shoppingcart.eBazzar.dto.ImageDto;
import com.shoppingcart.eBazzar.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
