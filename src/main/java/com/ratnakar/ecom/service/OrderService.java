package com.ratnakar.ecom.service;

import com.ratnakar.ecom.dto.OrderRequestDTO;
import com.ratnakar.ecom.dto.OrderResponseDTO;
import com.ratnakar.ecom.model.Orders;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {
        Orders orders = new Orders();
        String orderId = "ORD"+UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        orders.setOrderId(orderId);
        orders.setCustomerName(orderRequestDTO.customerName());
        orders.setEmail(orderRequestDTO.email());
        orders.setStatus("PLACED");
        orders.setOrderDate(LocalDate.now());
        return null;
    }

    public List<OrderResponseDTO> getAllOrderResponses() {
        return null;
    }
}
