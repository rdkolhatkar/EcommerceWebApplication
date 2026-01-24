package com.ratnakar.ecom.dto;

import java.util.List;

public record OrderRequestDTO(String customerName, String email, List<OrderItemRequestDTO> items) {}
/*
Aggregates customer info + list of order items
List<OrderItemRequestDTO> = one-to-many mapping
*/
