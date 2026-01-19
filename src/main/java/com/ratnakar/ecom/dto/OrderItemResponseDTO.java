package com.ratnakar.ecom.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        String productName,
        int quantity,
        BigDecimal totalPrice
) {}
