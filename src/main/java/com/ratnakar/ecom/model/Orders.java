package com.ratnakar.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String orderId;
    private String customerName;
    private String email;
    private String status;
    private LocalDate orderDate;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;
}
