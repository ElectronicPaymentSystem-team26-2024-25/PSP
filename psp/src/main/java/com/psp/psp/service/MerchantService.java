package com.psp.psp.service;

import com.psp.psp.converter.MerchantConverter;
import com.psp.psp.dto.MerchantDto;
import com.psp.psp.model.Merchant;
import com.psp.psp.repository.interfaces.IMerchantRepository;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    private final IMerchantRepository iMerchantRepository;

    public MerchantService(IMerchantRepository iMerchantRepository) {
        this.iMerchantRepository = iMerchantRepository;
    }

    public MerchantDto getMerchant(String businessEmail){
        Merchant merchant = iMerchantRepository.findByBusinessEmail(businessEmail);
        if(merchant == null) return null; //TODO: Return error code
        return MerchantConverter.convertToDto(merchant);
    }
}
