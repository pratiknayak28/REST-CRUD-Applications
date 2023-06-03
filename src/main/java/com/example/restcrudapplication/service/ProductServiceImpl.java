package com.example.restcrudapplication.service;

import com.example.restcrudapplication.exceptionhandlers.productNotFoundException;
import com.example.restcrudapplication.model.Product;
import com.example.restcrudapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public int saveProduct(Product p) {
        // JDK-10 , Local variable type inference
        var cost = p.getProdCost();
        if (cost != null && cost > 0) {
            var gst = cost * 0.12;
            var disc = cost * 0.20;
            p.setProdDisc(disc);
            p.setProdGst(gst);
            p = repo.save(p);
        }
        return p.getProdId();
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product searchOneProduct(int id) {
        //  Optional<Product> product = repo.findById(id);
        //  if(product.isPresent()){
        //     return  product.get();
        /// }else{
        //    throw new productNotFoundException("Product having id:"+id+" is not present");
        //  }
        return repo.findById(id).orElseThrow(() -> new productNotFoundException("The product with id : " + id + " is not present"));
    }

    @Override
    public void deleteProductById(int id) {
        Product prod = searchOneProduct(id);
        repo.delete(prod);
        //repo.deleteById(id);


    }

    @Override
    public void updateProductById(Product p) {
        var cost = p.getProdCost();
        if (cost != null && cost > 0) {
            var gst = cost * 0.12;
            var disc = cost * 0.20;
            p.setProdDisc(disc);
            p.setProdGst(gst);
        }
        repo.save(p);

    }

    public boolean isProductExist(int id) {
        return repo.existsById(id);
    }

    @Override
    public void updateProductCodeById(String productCode, int id) {
        repo.updateProdCodeById(id, productCode);
    }

    @Override
    public void updateProductCodeByCost(int id, Double prodCost) {
        repo.updateProdCostById(id, prodCost);
    }


}
