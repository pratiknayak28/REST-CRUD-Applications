package com.example.restcrudapplication.controllers;

import com.example.restcrudapplication.exceptionhandlers.productNotFoundException;
import com.example.restcrudapplication.model.Product;
import com.example.restcrudapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        int p = service.saveProduct(product);
        return new ResponseEntity<>("Product Saved " + p, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> list = service.getAllProducts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOneProduct(@PathVariable int id) {
        Product prod = service.searchOneProduct(id);
        return new ResponseEntity<>(prod, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        service.deleteProductById(id);
        return new ResponseEntity<>("The product with id " + id + " is deleted", HttpStatus.OK);
    }

    // FOR Complete Data update use PUT , for partial data update use PATCH -> good practice
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product prod) {
        if (service.isProductExist(id)) {
            prod.setProdId(id);
            service.updateProductById(prod);
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.OK);
        } else {
            throw new productNotFoundException("Product " + id + " does not exist to update !");
        }
    }

    @PatchMapping("/{id}/{code}")
    public ResponseEntity<String> updateProductCode(@PathVariable int id, @PathVariable String code) {

        if (service.isProductExist(id)) {
            service.updateProductCodeById(code, id);
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.OK);
        } else {
            throw new productNotFoundException("Product " + id + " does not exist to update !");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProductCost(@PathVariable int id, @RequestParam(name = "cost") Double prodCost) {

        if (service.isProductExist(id)) {
            service.updateProductCodeByCost(id, prodCost);
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.OK);
        } else {
            throw new productNotFoundException("Product " + id + " does not exist to update !");
        }
    }

}
