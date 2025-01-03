package com.shoppingcart.eBazzar.service.product;

import com.shoppingcart.eBazzar.dto.ProductDto;
import com.shoppingcart.eBazzar.model.Product;
import com.shoppingcart.eBazzar.requests.AddProductRequest;
import com.shoppingcart.eBazzar.requests.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
    ProductDto convertToDto(Product product);

    List<ProductDto> getConvertedProducts(List<Product> products);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);
}
