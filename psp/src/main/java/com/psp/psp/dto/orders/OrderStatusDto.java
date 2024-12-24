package com.psp.psp.dto.orders;

public class OrderStatusDto {
    private int orderId;
    private String orderStatus;
    public OrderStatusDto(){}

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
