package com.example.restcrudapplication.exceptionhandlers;

import com.example.restcrudapplication.model.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {
    /*
     * Below method is called by FrontController. When ProductNotFoundException
     * is thrown by any Rest controller (after throwing exception)
     */

    //--->Message comes in String format
	/*
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handleProductNotFoundException(
			ProductNotFoundException pne
			)
	{
		return new ResponseEntity<String>(
				pne.getMessage(),HttpStatus.NOT_FOUND
				);
	}*/
    //--->Message comes in JSON format

    @ExceptionHandler(productNotFoundException.class)
    public ResponseEntity<ErrorData> handleProductNotFoundException(productNotFoundException pne) {
        return new ResponseEntity<>(new ErrorData(pne.getMessage(), new Date().toString(), "Product"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorData> Exception(Exception e) {
        return new ResponseEntity<>(new ErrorData(Arrays.toString(e.getStackTrace()), new Date().toString(), "Product"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
