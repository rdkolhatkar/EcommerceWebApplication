package com.ratnakar.ecom.service;

import com.ratnakar.ecom.model.Products;
import com.ratnakar.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public ResponseEntity<List<Products>> getAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }
}
