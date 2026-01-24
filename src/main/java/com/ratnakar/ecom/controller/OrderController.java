package com.ratnakar.ecom.controller;

import com.ratnakar.ecom.dto.OrderRequestDTO;
import com.ratnakar.ecom.dto.OrderResponseDTO;
import com.ratnakar.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a Spring REST controller (from spring-web dependency). Handles HTTP requests.
@RequestMapping("/api") // All routes in this class will start with /api
@CrossOrigin // Allows cross-origin requests (for frontend apps running on a different domain/port)
public class OrderController {

    @Autowired // Spring automatically injects the OrderService bean here
    private OrderService orderService;

    @PostMapping("/orders/place") // Handles POST requests to /api/orders/place
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        // @RequestBody tells Spring to deserialize JSON request body into OrderRequestDTO object
        OrderResponseDTO orderResponse = orderService.placeOrder(orderRequestDTO);
        // Calls service layer to process the order

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        // Wraps response in ResponseEntity with HTTP 201 Created status
    }

    @GetMapping("/orders") // Handles GET requests to /api/orders
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        List<OrderResponseDTO> responseDTOS = orderService.getAllOrderResponses();
        // Calls service layer to fetch all orders

        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
        // Returns HTTP 200 with list of orders
    }
}
