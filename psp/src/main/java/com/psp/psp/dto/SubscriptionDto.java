package com.psp.psp.dto;


import java.util.List;

public class SubscriptionDto {

    public String getMerchantEmail() {
        return merchantEmail;
    }

    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }

    public List<PaymentMethodInfoDto> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethodInfoDto> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    private String merchantEmail;
    private List<PaymentMethodInfoDto> paymentMethods;
}
