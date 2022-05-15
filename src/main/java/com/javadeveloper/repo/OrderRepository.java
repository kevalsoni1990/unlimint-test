package com.javadeveloper.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javadeveloper.model.OrderData;

public interface OrderRepository extends JpaRepository<OrderData, Long>{

}
