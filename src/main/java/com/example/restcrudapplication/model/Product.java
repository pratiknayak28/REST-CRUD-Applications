package com.example.restcrudapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer prodId ;

    private String prodCode ;
    private Double prodCost ;

    private Double prodGst ;
    private Double prodDisc ;
}
