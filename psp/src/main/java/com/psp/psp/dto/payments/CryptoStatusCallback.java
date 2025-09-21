package com.psp.psp.dto.payments;

public class CryptoStatusCallback {
    private String merchantOrderId; // npr. "1001"
    private String status;          // "SUCCESS" | "FAILED" | "TIMEOUT" | "PENDING"
    private String txid;            // blockchain hash (može biti null)

    public String getMerchantOrderId() { return merchantOrderId; }
    public void setMerchantOrderId(String merchantOrderId) { this.merchantOrderId = merchantOrderId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTxid() { return txid; }
    public void setTxid(String txid) { this.txid = txid; }
}