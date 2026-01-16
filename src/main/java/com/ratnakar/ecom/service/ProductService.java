package com.ratnakar.ecom.service;

import com.ratnakar.ecom.model.ProductRequestDTO;
import com.ratnakar.ecom.model.Products;
import com.ratnakar.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products getProductById(long id) {
        return productRepository.findById(id).orElse(new Products(-1));
    }

    public Products addProduct(ProductRequestDTO dto, MultipartFile image) throws IOException {
        Products product = new Products();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setReleaseDate(dto.getReleaseDate());
        product.setProductAvailable(dto.isProductAvailable());
        product.setStockQuantity(dto.getStockQuantity());

        if (image != null && !image.isEmpty()) {
            product.setImageName(image.getOriginalFilename());
            product.setImageType(image.getContentType());
            product.setImageData(image.getBytes());
        }

        return productRepository.save(product);
    }
}
