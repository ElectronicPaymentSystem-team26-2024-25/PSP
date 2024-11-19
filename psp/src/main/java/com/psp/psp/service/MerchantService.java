package com.psp.psp.service;

import com.psp.psp.converter.MerchantConverter;
import com.psp.psp.dto.MerchantDto;
import com.psp.psp.dto.PaymentMethodDto;
import com.psp.psp.model.Merchant;
import com.psp.psp.model.PaymentSubscription;
import com.psp.psp.repository.interfaces.IMerchantRepository;
import com.psp.psp.repository.interfaces.IPaymentSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    private final IMerchantRepository iMerchantRepository;
    private final IPaymentSubscriptionRepository iPaymentSubscriptionRepository;

    public MerchantService(IMerchantRepository iMerchantRepository, IPaymentSubscriptionRepository iPaymentSubscriptionRepository) {
        this.iMerchantRepository = iMerchantRepository;
        this.iPaymentSubscriptionRepository = iPaymentSubscriptionRepository;
    }

    public MerchantDto getMerchant(String businessEmail){
        Merchant merchant = iMerchantRepository.findByBusinessEmail(businessEmail);
        if(merchant == null) return null; //TODO: Return error code
        return MerchantConverter.convertToDto(merchant);
    }

    public ArrayList<PaymentMethodDto> getSubscribedPayments(String businessEmail){
        Merchant merchant = iMerchantRepository.findByBusinessEmail(businessEmail);
        if(merchant == null) return null; //TODO: Return error code, throw Exception
        ArrayList<PaymentSubscription> subscriptions = new ArrayList<>();
        subscriptions = (ArrayList<PaymentSubscription>) iPaymentSubscriptionRepository.findByMerchantId(merchant.getId());
        if(subscriptions.size() < 1) return null; //TODO: Return error code, throw Exception
        ArrayList<PaymentMethodDto> dtos = new ArrayList<>();
        subscriptions.forEach(paymentSubscription -> { //TODO: Change this after creating file that will store information about payment methods
            PaymentMethodDto dto = new PaymentMethodDto();
            dto.setName("Name of payment + id:" + paymentSubscription.getPaymentMethodId());
            dtos.add(dto);
        });
        return dtos;
    }
}
