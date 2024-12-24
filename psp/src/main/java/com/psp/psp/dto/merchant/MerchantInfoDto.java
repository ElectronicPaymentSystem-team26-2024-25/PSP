package com.psp.psp.dto.merchant;

public class MerchantInfoDto {
    private String merchantPassword;
    private String port;
    public MerchantInfoDto(){}

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
