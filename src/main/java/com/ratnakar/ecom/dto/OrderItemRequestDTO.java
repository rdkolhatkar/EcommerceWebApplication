package com.ratnakar.ecom.dto;

public record OrderItemRequestDTO(long productId, int quantity) {}
/*
record = immutable data class introduced in Java 16+
Automatically generates:
    1) Constructor
    2) Getters (as productId() and quantity())
    3) equals(), hashCode(), toString()
    4) Saves boilerplate compared to normal POJOs
*/