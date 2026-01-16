package com.ratnakar.ecom.repository;

import com.ratnakar.ecom.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {

}
