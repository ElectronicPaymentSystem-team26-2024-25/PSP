package com.psp.psp.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "merchant_id")
    private UUID MERCHANT_ID;

    @Column(nullable = false, unique = true, name = "merchant_password")
    private UUID MERCHANT_PASSWORD;

    //MerchantTimestamp?

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_email")
    private String businessEmail;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "legal_lastname")
    private String legalLastname;


    public Merchant() {
    }

    public Merchant(Long id, UUID merchantId, UUID merchantPassword, String businessName, String businessEmail, String legalName, String legalLastname) {
        this.id = id;
        this.MERCHANT_ID = merchantId;
        this.MERCHANT_PASSWORD = merchantPassword;
        this.businessName = businessName;
        this.businessEmail = businessEmail;
        this.legalName = legalName;
        this.legalLastname = legalLastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getMerchantId() {
        return MERCHANT_ID;
    }

    public void setMerchantId(UUID merchantId) {
        this.MERCHANT_ID = merchantId;
    }

    public UUID getMerchantPassword() {
        return MERCHANT_PASSWORD;
    }

    public void setMerchantPassword(UUID merchantPassword) {
        this.MERCHANT_PASSWORD = merchantPassword;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalLastname() {
        return legalLastname;
    }

    public void setLegalLastname(String legalLastname) {
        this.legalLastname = legalLastname;
    }
}
