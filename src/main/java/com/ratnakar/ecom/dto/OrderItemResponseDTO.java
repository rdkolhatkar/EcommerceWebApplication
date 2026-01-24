package com.ratnakar.ecom.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(String productName, int quantity, BigDecimal totalPrice) {}
/*
Holds response info per item
BigDecimal = precise decimal type for money
*/
