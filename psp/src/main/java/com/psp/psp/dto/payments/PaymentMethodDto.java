package com.psp.psp.dto.payments;

import com.psp.psp.enumerations.PaymentType;
import lombok.Getter;

@Getter
public class PaymentMethodDto {
    private Long id;
    private String name;
    private PaymentType type;
    private String address;
    private String endpoint;

    public void setName(String name) {
        this.name = name;
    }
    public void setType(PaymentType type) {
        this.type = type;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
