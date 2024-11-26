package com.psp.psp.dto;

public class CreateOrderRequest {
    private String merchantId;
    private String merchantPassword;
    private int amount;
    private int merchantOrderId;
    public CreateOrderRequest(){}

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int orderId) {
        this.merchantOrderId = orderId;
    }
}
