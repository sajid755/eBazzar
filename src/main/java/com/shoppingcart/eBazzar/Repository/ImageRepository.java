package com.shoppingcart.eBazzar.Repository;

import com.shoppingcart.eBazzar.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Collection<Object> findByProductId(Long id);
}
