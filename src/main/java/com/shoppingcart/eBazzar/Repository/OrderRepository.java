package com.shoppingcart.eBazzar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.eBazzar.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
