package com.Ecommerce.Ecommerce.DTO;

import com.Ecommerce.Ecommerce.Model.OrderItem;
import com.Ecommerce.Ecommerce.Model.OrderStatus;

import java.util.List;

public class OrderDetailsDTO {

    private Long orderId;
    private Double totalAmount;
    private OrderStatus status;
    private Long userId;
    private List<OrderItem> items;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}