package com.shoppingcart.eBazzar.Repository.category;

import com.shoppingcart.eBazzar.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
