package com.ratnakar.ecom.controller;

import com.ratnakar.ecom.model.ProductRequestDTO;
import com.ratnakar.ecom.model.Products;
import com.ratnakar.ecom.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable long id) {
        Products product = productService.getProductById(id);
        if (product.getId() > 0) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            log.error("Product Not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @RequestPart("product") ProductRequestDTO dto,
            @RequestPart("imageFile") MultipartFile imageFile) {

        try {
            Products savedProduct = productService.addProduct(dto, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            log.error("Error saving product", e);
            return new ResponseEntity<>("Failed to save product image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/product/{productId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable long productId) {
        Products product = productService.getProductById(productId);
        if (product.getId() <= 0 || product.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(product.getImageType()))
                .body(product.getImageData());
    }

}
