package com.psp.psp.model;

import com.psp.psp.enumerations.PaymentType;
import jakarta.persistence.*;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "type")
    private PaymentType type;
    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    public PaymentMethod() {
    }

    public PaymentMethod(Long id, String name, PaymentType type, boolean isActive) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}