package com.shoppingcart.eBazzar.service.product;

import com.shoppingcart.eBazzar.Repository.CategoryRepository;
import com.shoppingcart.eBazzar.Repository.ImageRepository;
import com.shoppingcart.eBazzar.Repository.ProductRepository;
import com.shoppingcart.eBazzar.dto.ImageDto;
import com.shoppingcart.eBazzar.dto.ProductDto;
import com.shoppingcart.eBazzar.exception.ProductNotFoundException;
import com.shoppingcart.eBazzar.exception.ResourceNotFoundException;
import com.shoppingcart.eBazzar.model.Category;
import com.shoppingcart.eBazzar.model.Product;
import com.shoppingcart.eBazzar.requests.AddProductRequest;
import com.shoppingcart.eBazzar.requests.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, the save it as a new category
        // The set as the new product category.

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete, // Action if product exists
                        () -> {
                            throw new ProductNotFoundException("Product Not Found!");
                        } // Action if product doesn't exist
                );
    }

    @Override
    public Product updateProduct(UpdateProductRequest req, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("The resource not found Exception"));
        existingProduct = updateExistingProduct(existingProduct, req);
        return productRepository.save(existingProduct);

    }

    public Product updateExistingProduct(Product existingProduct, UpdateProductRequest req) {
        existingProduct.setName(req.getName());
        existingProduct.setBrand(req.getBrand());
        existingProduct.setPrice(req.getPrice());
        existingProduct.setInventory(req.getInventory());
        existingProduct.setDescription(req.getDescription());

        // Check if category name is provided in the request
        if (req.getCategory() != null && req.getCategory().getName() != null) {
            // Fetch the Category by its name
            Category category = categoryRepository.findByName(req.getCategory().getName());

            if (category == null) {
                throw new ResourceNotFoundException("Category not found");
            }

            existingProduct.setCategory(category);
        }

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setImages(
                imageRepository.findByProductId(product.getId()).stream()
                        .map(image -> modelMapper.map(image, ImageDto.class))
                        .toList());
        return productDto;
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

}
