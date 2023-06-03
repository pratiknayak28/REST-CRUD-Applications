package com.example.restcrudapplication.controllers;

import com.example.restcrudapplication.exceptionhandlers.productNotFoundException;
import com.example.restcrudapplication.model.Product;
import com.example.restcrudapplication.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductRestController.class);
    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {

        LOG.info("Entered into saveProduct()");
        ResponseEntity<String> resp;
        try {
            int p = service.saveProduct(product);
            resp = new ResponseEntity<>("Product Saved with id :" + p, HttpStatus.CREATED);
            LOG.info("PRODUCT SAVED WITH ID: {}", p);
        } catch (Exception e) {
            resp = new ResponseEntity<>("Unable to process save product", HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO SAVE PRODUCT {}", e.getMessage());
        }
        LOG.info("Leaving saveProduct() method");
        return resp;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        ResponseEntity<?> resp;
        LOG.info("inside getAllProducts() method");
        try {
            List<Product> list = service.getAllProducts();
            resp = new ResponseEntity<>(list, HttpStatus.OK);
            LOG.info("PRODUCTS LIST RETURNED");
        } catch (Exception e) {
            resp = new ResponseEntity<>("Unable to get all products", HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO GET ALL PRODUCT {}", e.getMessage());
        }
        LOG.info("Leaving getAllProducts() method");
        return resp;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProduct(@PathVariable int id) {

        ResponseEntity<?> resp;
        LOG.info("inside getOneProduct() method");
        try {
            Product prod = service.searchOneProduct(id);
            LOG.info("PRODUCT FOUND {}", prod.toString());
            return new ResponseEntity<>(prod, HttpStatus.OK);
        } catch (productNotFoundException p) {
            LOG.error("PRODUCT DOES NOT EXIST {}", p.getMessage());
            throw p;
        } catch (Exception e) {
            resp = new ResponseEntity<>("Unable to search the product", HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO SEARCH PRODUCT {}", e.getMessage());
        }
        LOG.info("Leaving getOneProduct() method");
        return resp;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        ResponseEntity<String> resp;
        LOG.info("inside deleteProduct() method");
        try {
            service.deleteProductById(id);
            LOG.info("PRODUCT DELETED");
            return new ResponseEntity<>("The product with id " + id + " is deleted", HttpStatus.OK);
        } catch (productNotFoundException p) {
            LOG.error("PRODUCT DOES NOT EXIST {}", p.getMessage());
            throw p;
        } catch (Exception e) {
            resp = new ResponseEntity<>("Unable to delete the product", HttpStatus.INTERNAL_SERVER_ERROR);
            LOG.error("UNABLE TO SEARCH PRODUCT {}", e.getMessage());
        }
        LOG.info("Leaving deleteProduct() method");
        return resp;
    }

    // FOR Complete Data update use PUT , for partial data update use PATCH -> good practice
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product prod) {
        LOG.info("inside updateProduct() method");
        if (service.isProductExist(id)) {
            prod.setProdId(id);
            service.updateProductById(prod);
            LOG.info("leaving updateProduct() method");
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.RESET_CONTENT);
        } else {
            LOG.info("leaving updateProduct() method");
            throw new productNotFoundException("Product " + id + " does not exist to update !");

        }

    }

    @PatchMapping("/{id}/{code}")
    public ResponseEntity<String> updateProductCode(@PathVariable int id, @PathVariable String code) {
        LOG.info("inside updateProductCode() method");
        if (service.isProductExist(id)) {
            service.updateProductCodeById(code, id);
            LOG.info("leaving updateProductCode() method");
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.PARTIAL_CONTENT);
        } else {
            LOG.info("leaving updateProductCode() method");
            throw new productNotFoundException("Product " + id + " does not exist to update !");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProductCost(@PathVariable int id, @RequestParam(name = "cost") Double prodCost) {
        LOG.info("inside updateProductCost() method");
        if (service.isProductExist(id)) {
            service.updateProductCodeByCost(id, prodCost);
            LOG.info("leaving updateProductCost() method");
            return new ResponseEntity<>("The product with id " + id + " updated", HttpStatus.PARTIAL_CONTENT);
        } else {
            LOG.info("leaving updateProductCost() method");
            throw new productNotFoundException("Product " + id + " does not exist to update !");
        }
    }

}
