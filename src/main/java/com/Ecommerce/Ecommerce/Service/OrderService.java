package com.Ecommerce.Ecommerce.Service;

import com.Ecommerce.Ecommerce.DTO.OrderDetailsDTO;
import com.Ecommerce.Ecommerce.Model.*;
import com.Ecommerce.Ecommerce.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Long userId) {

        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PENDING);

        double totalAmount = 0;

        Order savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            if (cartItem.getQuantity() > product.getStock()) {
                throw new RuntimeException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }

            double itemTotal =
                    product.getPrice() * cartItem.getQuantity();

            totalAmount += itemTotal;

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );

            productRepository.save(product);
        }

        savedOrder.setTotalAmount(totalAmount);

        Order finalOrder = orderRepository.save(savedOrder);

        cartItemRepository.deleteAllByCartId(cart.getId());

        return finalOrder;
    }

    public Order getOrderById(Long orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUser(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setStatus(status);

        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        // Check if order can be cancelled
        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED ||
                order.getStatus() == OrderStatus.CANCELLED) {

            throw new RuntimeException(
                    "Order cannot be cancelled"
            );
        }

        // Get order items
        List<OrderItem> orderItems =
                orderItemRepository.findByOrder(order);

        // Restore product stock
        for (OrderItem orderItem : orderItems) {

            Product product = orderItem.getProduct();

            product.setStock(
                    product.getStock() + orderItem.getQuantity()
            );

            productRepository.save(product);
        }

        // Change order status
        order.setStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }

    public OrderDetailsDTO getOrderDetails(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        List<OrderItem> items =
                orderItemRepository.findByOrder(order);

        OrderDetailsDTO dto = new OrderDetailsDTO();

        dto.setOrderId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setUserId(order.getUser().getId());
        dto.setItems(items);

        return dto;
    }
}