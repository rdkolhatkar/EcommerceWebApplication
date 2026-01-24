package com.ratnakar.ecom.controller;

import com.ratnakar.ecom.dto.ProductRequestDTO;
import com.ratnakar.ecom.model.Products;
import com.ratnakar.ecom.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j // Lombok annotation to automatically add a logger
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        // Returns all products with HTTP 200
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable long id) {
        // @PathVariable extracts {id} from URL
        Products product = productService.getProductById(id);
        if (product.getId() > 0) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            log.error("Product Not Found"); // Logs error to console/log files
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if not found
        }
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @RequestPart("product") ProductRequestDTO dto,
            @RequestPart("imageFile") MultipartFile imageFile) {
        // @RequestPart used for multipart requests (file + JSON)
        // MultipartFile represents uploaded file

        try {
            Products savedProduct = productService.addProduct(dto, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            log.error("Error saving product", e);
            return new ResponseEntity<>("Failed to save product image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable long productId) {
        Products product = productService.getProductById(productId);
        if (product.getImageData() == null) {
            return ResponseEntity.notFound().build(); // 404 if no image
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=\"" + product.getImageName() + "\"")
                .contentType(MediaType.parseMediaType(product.getImageType()))
                .body(product.getImageData());
        // Returns image bytes with proper headers for browser rendering
    }

    @PutMapping(value = "/product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduct(
            @PathVariable long id,
            @RequestPart("product") ProductRequestDTO dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            productService.updateProduct(id, dto, imageFile);
            return ResponseEntity.ok("Updated Successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        Products product = productService.getProductById(id);
        if(product != null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Products>> searchProducts(@RequestParam String keyword){
        List<Products> products = productService.searchProducts(keyword);
        System.out.println("Searching with Keyword : "+keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}/image/raw")
    public ResponseEntity<byte[]> getProductImageRaw(@PathVariable long productId) {
        Products product = productService.getProductById(productId);
        if (product.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }
        String imageType = product.getImageType();
        if (imageType == null || imageType.isEmpty()) {
            imageType = "image/jpeg"; // default fallback
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=" + product.getImageName())
                .contentType(MediaType.IMAGE_JPEG)
                .body(product.getImageData());
    }
}

