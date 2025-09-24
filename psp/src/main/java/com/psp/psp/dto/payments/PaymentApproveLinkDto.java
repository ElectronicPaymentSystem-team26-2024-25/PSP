// src/main/java/com/psp/psp/dto/payments/PaymentApproveLinkDto.java
package com.psp.psp.dto.payments;

public class PaymentApproveLinkDto {
    private String message;  // FE expects { message: "<url>" }

    public PaymentApproveLinkDto() {}
    public PaymentApproveLinkDto(String message) { this.message = message; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
