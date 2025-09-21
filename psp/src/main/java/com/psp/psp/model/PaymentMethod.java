package com.psp.psp.model;

import com.psp.psp.enumerations.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;
    @Getter
    @Column(nullable = false, name = "name")
    private String name;
    @Getter
    @Column(nullable = false, name = "type")
    private PaymentType type;
    @Getter
    @Column(nullable = false, name = "address")
    private String address;
    @Getter
    @Column(nullable = false, name = "endpoint")
    private String endpoint;

    public PaymentMethod() {
    }

    public PaymentMethod(Long id, String name, PaymentType type, String address, String endpoint) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.endpoint = endpoint;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}