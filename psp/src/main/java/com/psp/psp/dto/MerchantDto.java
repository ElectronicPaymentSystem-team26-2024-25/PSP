package com.psp.psp.dto;

import jakarta.persistence.Column;

public class MerchantDto {
    private String merchantId;
    private String merchantPassword;
    private String businessName;
    private String businessEmail;
    private String legalName;
    private String legalLastname;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
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
