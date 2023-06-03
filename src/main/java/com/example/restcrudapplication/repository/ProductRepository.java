package com.example.restcrudapplication.repository;

import com.example.restcrudapplication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    //Note:
    //Query is used to define custom SELECT query
    //If we want to define non-select query write @Query and also apply @Modifying
    //that indicates to Data JPA, this is not SELECT it is either UPDATE/DELETE.
    //Also apply @Transactional for this method, Which does commit(on success)
    //rollback (on failure).
    //For non-select custom query = Query + @Modifying + @Transactional
    @Transactional
    @Modifying
    @Query("UPDATE Product SET prodCode=:prodCode where prodId=:prodId")
    void updateProdCodeById(Integer prodId, String prodCode);

    @Transactional
    @Modifying
    @Query("UPDATE Product SET prodCost=:prodCost where prodId=:prodId")
    void updateProdCostById(int prodId, Double prodCost);
}
