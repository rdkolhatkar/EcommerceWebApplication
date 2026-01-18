package com.ratnakar.ecom.repository;

import com.ratnakar.ecom.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
    // Below is the JPQL(Java Persistence Query Language) syntax for JPA Query
    @Query("""
        SELECT p FROM Products p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Products> searchProducts(@Param("keyword") String keyword);
}
