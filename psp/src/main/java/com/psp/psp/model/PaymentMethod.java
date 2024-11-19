package com.psp.psp.model;

public class PaymentMethod {

    private Long id;
    private String name;
    private String apiEndpointUrl;
    private boolean isActive;

    //TODO: Add credentials_info field, that will be used for authentication between payment method and PSP

    public PaymentMethod() {
    }

    public PaymentMethod(Long id, String name, String apiEndpointUrl, boolean isActive) {
        this.id = id;
        this.name = name;
        this.apiEndpointUrl = apiEndpointUrl;
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

    public String getApiEndpointUrl() {
        return apiEndpointUrl;
    }

    public void setApiEndpointUrl(String apiEndpointUrl) {
        this.apiEndpointUrl = apiEndpointUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
