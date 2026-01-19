package com.ratnakar.ecom.dto;

public record OrderItemRequestDTO(
        int productId,
        int quantity
) {}
