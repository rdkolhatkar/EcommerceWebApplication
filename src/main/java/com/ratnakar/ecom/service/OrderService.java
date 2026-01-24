package com.ratnakar.ecom.service;

import com.ratnakar.ecom.dto.OrderItemRequestDTO;
import com.ratnakar.ecom.dto.OrderItemResponseDTO;
import com.ratnakar.ecom.dto.OrderRequestDTO;
import com.ratnakar.ecom.dto.OrderResponseDTO;
import com.ratnakar.ecom.exception.InsufficientStockException;
import com.ratnakar.ecom.exception.ProductNotFoundException;
import com.ratnakar.ecom.model.OrderItems;
import com.ratnakar.ecom.model.Orders;
import com.ratnakar.ecom.model.Products;
import com.ratnakar.ecom.repository.OrderRepository;
import com.ratnakar.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository,
                        OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    /*
    OrderService.placeOrder() method
    Generates unique order ID using UUID
    Validates stock before saving
    Updates stock atomically
    Maps entities → DTOs using mapToResponse()
    @Transactional ensures all-or-nothing DB operation
    */
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {

        Orders order = new Orders();
        order.setOrderId("ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());
        order.setOrderItems(new ArrayList<>());

        for (OrderItemRequestDTO item : request.items()) {

            Products product = productRepository.findById(item.productId())
                    .orElseThrow(() ->
                            new ProductNotFoundException("Product not found: " + item.productId())
                    );

            if (product.getStockQuantity() < item.quantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            product.setStockQuantity(product.getStockQuantity() - item.quantity());

            OrderItems orderItem = OrderItems.builder()
                    .product(product)
                    .quantity(item.quantity())
                    .totalPrice(
                            product.getPrice()
                                    .multiply(BigDecimal.valueOf(item.quantity()))
                    )
                    .build();

            order.addOrderItem(orderItem);
        }

        Orders savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    public List<OrderResponseDTO> getAllOrderResponses() {
        return orderRepository.findAll()
                .stream()// Converts List<Orders> to Stream<Orders>
                .map(this::mapToResponse)
                // :: = method reference (shortcut for lambda)
                // Equivalent to .map(order -> this.mapToResponse(order))
                .toList();
                // Collects Stream back into List<OrderResponseDTO>
    }

    private OrderResponseDTO mapToResponse(Orders order) {

        List<OrderItemResponseDTO> items = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getOrderId(),
                order.getCustomerName(),
                order.getEmail(),
                order.getStatus(),
                order.getOrderDate(),
                items
        );
    }
}
