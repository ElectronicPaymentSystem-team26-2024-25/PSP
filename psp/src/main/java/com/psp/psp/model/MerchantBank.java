package com.psp.psp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
public class MerchantBank {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(nullable = false, name = "MERCHANT_ID")
    private UUID merchantId;
    @Column(nullable = false, name = "MERCHANT_PASSWORD")
    private UUID merchantPassword;
    @Column(nullable = false, name = "PORT")
    private String port;
    @Column(nullable = false, name = "BANK_NAME")
    private String bankName;
    public MerchantBank(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public UUID getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(UUID merchantPassword) {
        this.merchantPassword = merchantPassword;
    }
}
