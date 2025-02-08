package com.shoppingcart.eBazzar.service.product;

import com.shoppingcart.eBazzar.Repository.CategoryRepository;
import com.shoppingcart.eBazzar.Repository.ImageRepository;
import com.shoppingcart.eBazzar.Repository.ProductRepository;
import com.shoppingcart.eBazzar.dto.ProductDto;
import com.shoppingcart.eBazzar.dto.requests.AddProductRequestDto;
import com.shoppingcart.eBazzar.dto.requests.UpdateProductRequest;
import com.shoppingcart.eBazzar.exception.ProductNotFoundException;
import com.shoppingcart.eBazzar.exception.ResourceNotFoundException;
import com.shoppingcart.eBazzar.model.Category;
import com.shoppingcart.eBazzar.model.Image;
import com.shoppingcart.eBazzar.model.Product;
import com.shoppingcart.eBazzar.utils.mapper.ImageMapper;
import com.shoppingcart.eBazzar.utils.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    private final CategoryIntegrationService categoryIntegrationService;

    @Override
    public Product addProduct(AddProductRequestDto request) throws ResourceNotFoundException {
        Category category = categoryIntegrationService.getCategoryByName(request.getCategory().getName());
        Product product = ProductMapper.INSTANCE.AddProductRequestDtoToProduct(request);
        product.setCategory(category);
        category.getProducts().add(product);
        return productRepository.save(product);
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
            Category category = categoryRepository.findByName(req.getCategory().getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            List<Product> products = category.getProducts();
            //baki ekahne.
            //products.forEach(product -> if(product.get));
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
        ProductDto productDto = ProductMapper.INSTANCE.productToProductDto(product);
        productDto.setImages(
                imageRepository.findByProductId(product.getId()).stream()
                        .map(image -> ImageMapper.INSTANCE.imageToImageDto((Image) image))
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
