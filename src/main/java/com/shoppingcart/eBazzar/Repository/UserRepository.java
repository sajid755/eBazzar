package com.shoppingcart.eBazzar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.eBazzar.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

}
