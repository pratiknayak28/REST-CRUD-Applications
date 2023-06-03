package com.example.restcrudapplication.exceptionhandlers;

public class productNotFoundException extends RuntimeException{


    productNotFoundException(){
        super();
    }
    public productNotFoundException(String msg){
        super(msg);
    }
}
