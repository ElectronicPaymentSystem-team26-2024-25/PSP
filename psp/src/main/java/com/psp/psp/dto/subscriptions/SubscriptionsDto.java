package com.psp.psp.dto.subscriptions;


import com.psp.psp.dto.payments.PaymentMethodDto;

import java.util.List;

public class SubscriptionsDto {

    public String getMerchantEmail() {
        return merchantEmail;
    }

    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }

    public List<PaymentMethodDto> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethodDto> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    private String merchantEmail;
    private List<PaymentMethodDto> paymentMethods;
}
