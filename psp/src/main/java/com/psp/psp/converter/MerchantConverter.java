package com.psp.psp.converter;

import com.psp.psp.dto.MerchantDto;
import com.psp.psp.model.Merchant;
import org.springframework.stereotype.Component;

@Component
public class MerchantConverter{

    public static MerchantDto convertToDto(Merchant merchant){
        MerchantDto dto = new MerchantDto();
        dto.setId(merchant.getId());
        dto.setMerchantId(String.valueOf(merchant.getMerchantId()));
        dto.setMerchantPassword(String.valueOf(merchant.getMerchantPassword()));
        dto.setBusinessEmail(merchant.getBusinessEmail());
        dto.setBusinessName(merchant.getBusinessName());
        dto.setLegalName(merchant.getLegalName());
        dto.setLegalLastname(merchant.getLegalLastname());
        return dto;
    }
}
