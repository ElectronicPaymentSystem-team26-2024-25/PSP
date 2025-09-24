// src/main/java/com/psp/psp/dto/payments/CryptoInitForwardRequest.java
package com.psp.psp.dto.payments;

import java.math.BigDecimal;

public class CryptoInitForwardRequest {
    private String merchantOrderId;
    private BigDecimal amount;
    private String currency;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;

    public CryptoInitForwardRequest() { }

    public CryptoInitForwardRequest(String merchantOrderId, BigDecimal amount, String currency,
                                    String successUrl, String failedUrl, String errorUrl) {
        this.merchantOrderId = merchantOrderId;
        this.amount = amount;
        this.currency = currency;
        this.successUrl = successUrl;
        this.failedUrl = failedUrl;
        this.errorUrl = errorUrl;
    }

    public String getMerchantOrderId() { return merchantOrderId; }
    public void setMerchantOrderId(String merchantOrderId) { this.merchantOrderId = merchantOrderId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getSuccessUrl() { return successUrl; }
    public void setSuccessUrl(String successUrl) { this.successUrl = successUrl; }

    public String getFailedUrl() { return failedUrl; }
    public void setFailedUrl(String failedUrl) { this.failedUrl = failedUrl; }

    public String getErrorUrl() { return errorUrl; }
    public void setErrorUrl(String errorUrl) { this.errorUrl = errorUrl; }
}
