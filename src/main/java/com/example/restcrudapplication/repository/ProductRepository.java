package com.example.restcrudapplication.repository;

import com.example.restcrudapplication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
