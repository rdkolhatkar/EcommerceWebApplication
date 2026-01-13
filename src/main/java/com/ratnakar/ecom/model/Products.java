package com.ratnakar.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "product",
        schema = "product_schema"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String brand;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private String category;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "product_available")
    private boolean productAvailable;

    @Column(name = "stock_quantity")
    private int stockQuantity;
}
