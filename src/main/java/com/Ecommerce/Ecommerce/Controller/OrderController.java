package com.Ecommerce.Ecommerce.Controller;

import com.Ecommerce.Ecommerce.DTO.OrderDetailsDTO;
import com.Ecommerce.Ecommerce.Model.Order;
import com.Ecommerce.Ecommerce.Model.OrderStatus;
import com.Ecommerce.Ecommerce.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createOrder(
            @PathVariable Long userId) {

        Order order = orderService.createOrder(userId);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(
            @PathVariable Long orderId) {

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(
            @PathVariable Long userId) {

        List<Order> orders =
                orderService.getOrdersByUser(userId);

        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        Order order =
                orderService.updateOrderStatus(orderId, status);

        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(
            @PathVariable Long orderId) {

        Order order = orderService.cancelOrder(orderId);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetails(
            @PathVariable Long orderId) {

        OrderDetailsDTO details =
                orderService.getOrderDetails(orderId);

        return ResponseEntity.ok(details);
    }
}