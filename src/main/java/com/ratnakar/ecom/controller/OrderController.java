package com.ratnakar.ecom.controller;

import com.ratnakar.ecom.dto.OrderRequestDTO;
import com.ratnakar.ecom.dto.OrderResponseDTO;
import com.ratnakar.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponse = orderService.placeOrder(orderRequestDTO);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        List<OrderResponseDTO> responseDTOS = orderService.getAllOrderResponses();
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }
}
