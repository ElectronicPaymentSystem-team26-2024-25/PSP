package com.psp.psp.dto;

import com.psp.psp.enumerations.PaymentType;

public class PaymentMethodDto {
    private String name;
    private PaymentType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }
}
