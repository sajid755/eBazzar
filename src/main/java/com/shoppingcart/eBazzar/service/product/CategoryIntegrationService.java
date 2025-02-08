package com.shoppingcart.eBazzar.service.product;

import com.shoppingcart.eBazzar.model.Category;

public interface CategoryIntegrationService {
    Category addCategory(Category category);

    Category getCategoryByName(String name);
}
