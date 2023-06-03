package com.example.restcrudapplication.service;

import com.example.restcrudapplication.model.Product;

import java.util.List;

public interface ProductService {

    int saveProduct(Product p);

    List<Product> getAllProducts();

    Product searchOneProduct(int id);

    void deleteProductById(int id);

    void updateProductById(Product product);

    boolean isProductExist(int id);

    void updateProductCodeById(String productCode, int id);


    void updateProductCodeByCost(int id, Double prodCost);
}
