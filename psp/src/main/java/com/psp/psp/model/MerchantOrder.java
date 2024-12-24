package com.psp.psp.model;

import com.psp.psp.enumerations.PaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class MerchantOrder {
    @Id
    @Column(name = "MERCHANT_ORDER_ID")
    private int merchantOrderId;
    @Column(nullable = false, name = "MERCHANT_TIMESTAMP")
    private LocalDateTime merchantTimestamp;
    @Column(nullable = false, name = "AMOUNT")
    private int amount;
    @Column(nullable = false, name = "MERCHANT_ID")
    private String merchantId;
    @Column(nullable = true, name = "PAYMENT_ID")
    private String paymentId;
    @Column(nullable = false, name = "ORDER_STATUS")
    private PaymentStatus orderStatus;
    @Column(nullable = false, unique = true, name = "LINK_UUID")
    private UUID linkUUID;

    @Column(nullable = true, name = "FAIL_REASON")
    private String failReason;

    public MerchantOrder(){}

    public MerchantOrder(int orderId, LocalDateTime merchantTimestamp, int amount, String merchantId, String paymentId, PaymentStatus orderStatus) {
        this.merchantTimestamp = merchantTimestamp;
        this.amount = amount;
        this.merchantId = merchantId;
        this.paymentId = paymentId;
        this.orderStatus = orderStatus;
        this.merchantOrderId = orderId;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public LocalDateTime getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(LocalDateTime merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(PaymentStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getLinkUUID() {
        return linkUUID;
    }

    public void setLinkUUID(UUID linkUUID) {
        this.linkUUID = linkUUID;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
