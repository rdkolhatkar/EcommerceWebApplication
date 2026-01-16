package com.ratnakar.ecom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;
    private boolean productAvailable;
    private int stockQuantity;

    @JsonCreator
    public ProductRequestDTO(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("brand") String brand,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("category") String category,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("productAvailable") boolean productAvailable,
            @JsonProperty("stockQuantity") int stockQuantity
    ) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.category = category;
        this.releaseDate = releaseDate;
        this.productAvailable = productAvailable;
        this.stockQuantity = stockQuantity;
    }
}


