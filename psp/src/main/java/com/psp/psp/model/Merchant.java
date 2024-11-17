package com.psp.psp.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID merchantId;

    @Column(nullable = false, unique = true)
    private UUID merchantPassword;

    //MerchantTimestamp?

    @Column
    private String businessName;

    @Column
    private String businessEmail;

    @Column
    private String legalName;

    @Column
    private String legalLastname;


    public Merchant() {
    }

    public Merchant(Long id, UUID merchantId, UUID merchantPassword, String businessName, String businessEmail, String legalName, String legalLastname) {
        this.id = id;
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
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
        return merchantId;
    }

    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public UUID getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(UUID merchantPassword) {
        this.merchantPassword = merchantPassword;
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
