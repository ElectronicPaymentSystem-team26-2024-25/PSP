package com.psp.psp.model;

import com.psp.psp.enumerations.PaymentType;

import java.util.HashMap;

public class PaymentMethod {

    private Long id;
    private String name;
    private PaymentType type;
    private boolean isActive;
    private HashMap<String, String> endpoints;

    public PaymentMethod() {
    }

    public PaymentMethod(Long id, String name, String version, PaymentType type, boolean isActive, HashMap<String, String> endpoints) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isActive = isActive;
        this.endpoints = endpoints;
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

    public HashMap<String, String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(HashMap<String, String> endpoints) {
        this.endpoints = endpoints;
    }
}