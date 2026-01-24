package com.ratnakar.ecom.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data // Lombok generates getters, setters, toString, equals, hashCode
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
    /*
    @JsonCreator + @JsonProperty = lets Jackson map JSON keys to constructor parameters
    Saves default setters for immutable initialization
    */
    @JsonCreator // Jackson uses this constructor to create object from JSON
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


