package com.ratnakar.ecom.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponseDTO(
   String orderId,
   String customerName,
   String email,
   String status,
   LocalDate orderDate,
   List<OrderItemResponseDTO> items
) {}
