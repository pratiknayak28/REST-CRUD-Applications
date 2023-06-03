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

        ResponseEntity<String> resp;
        try {
            int p = service.saveProduct(product);
            resp = new ResponseEntity<>("Product Saved with id :" + p, HttpStatus.CREATED);
        } catch (Exception e) {
            resp = new ResponseEntity<>("Unable to process save product", HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resp;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        ResponseEntity<?> resp;
        try {
            List<Product> list = service.getAllProducts();
            resp = new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            resp = new ResponseEntity<>("Unable to get all products", HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resp;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProduct(@PathVariable int id) {

        ResponseEntity<?> resp;
        try {
            Product prod = service.searchOneProduct(id);
            return new ResponseEntity<>(prod, HttpStatus.OK);
        } catch (productNotFoundException p) {
            throw p;
        } catch (Exception e) {
            resp = new ResponseEntity<>("Unable to search the product", HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resp;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        ResponseEntity<String> resp;
        try {
            service.deleteProductById(id);
            return new ResponseEntity<>("The product with id " + id + " is deleted", HttpStatus.OK);
        } catch (productNotFoundException p) {
            throw p;
        } catch (Exception e) {
            resp = new ResponseEntity<>("Unable to delete the product", HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resp;
    }

    // FOR Complete Data update use PUT , for partial data update use PATCH -> good practice
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product prod) {
        if (service.isProductExist(id)) {
            prod.setProdId(id);
            service.updateProductById(prod);
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.RESET_CONTENT);
        } else {
            throw new productNotFoundException("Product " + id + " does not exist to update !");
        }
    }

    @PatchMapping("/{id}/{code}")
    public ResponseEntity<String> updateProductCode(@PathVariable int id, @PathVariable String code) {

        if (service.isProductExist(id)) {
            service.updateProductCodeById(code, id);
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.PARTIAL_CONTENT);
        } else {
            throw new productNotFoundException("Product " + id + " does not exist to update !");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProductCost(@PathVariable int id, @RequestParam(name = "cost") Double prodCost) {

        if (service.isProductExist(id)) {
            service.updateProductCodeByCost(id, prodCost);
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.PARTIAL_CONTENT);
        } else {
            throw new productNotFoundException("Product " + id + " does not exist to update !");
        }
    }

}
