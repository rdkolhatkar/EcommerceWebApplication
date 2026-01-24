package com.ratnakar.ecom.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product", schema = "product_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Auto increment primary key
    private Long id;

    @Column(nullable = false)// Not null column
    private String name;

    @Column(columnDefinition = "TEXT")// Longer text
    private String description;

    private String brand;

    @Column(precision = 10, scale = 2)// 10 digits total, 2 decimal places
    private BigDecimal price;

    private String category;

    @Column(name = "release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;

    @Column(name = "product_available")
    private boolean productAvailable;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "image_name")
    @JsonIgnore // Hides field from JSON responses
    private String imageName;

    @Column(name = "image_type")
    @JsonIgnore
    private String imageType;
    // @Lob -> Large object for byte[] (image)
    @Lob // @Lob tells JPA/Hibernate that a field should be stored in the database as a large object, used for storing
    @JsonIgnore
//    @Column(name = "image_data", columnDefinition = "BYTEA")
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    // Constructor for "not found" placeholder
    public Products(long id) {
        this.id = id;
    }// Placeholder constructor
}
/*
    @Entity = JPA entity, maps to DB table
    @Id + @GeneratedValue = primary key auto-increment
    @Lob = large object (for images)
    @JsonIgnore = prevents returning sensitive/internal data in JSON
    @JsonFormat = custom date format
*/
