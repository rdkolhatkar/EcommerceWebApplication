package com.ratnakar.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
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
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();
    public void addOrderItem(OrderItems item) {
        orderItems.add(item);
        item.setOrder(this);// Maintain both sides of relationship
    }
}
/*
    mappedBy = inverse side of relationship
    cascade = ALL → saves/deletes child entities automatically
    orphanRemoval = true → deletes removed children automatically
*/
