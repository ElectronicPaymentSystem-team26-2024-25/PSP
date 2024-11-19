package com.psp.psp.service;

import com.psp.psp.converter.MerchantConverter;
import com.psp.psp.converter.PaymentMethodConverter;
import com.psp.psp.dto.MerchantDto;
import com.psp.psp.dto.PaymentMethodInfoDto;
import com.psp.psp.model.Merchant;
import com.psp.psp.model.PaymentMethod;
import com.psp.psp.model.PaymentSubscription;
import com.psp.psp.repository.interfaces.IMerchantRepository;
import com.psp.psp.repository.interfaces.IPaymentSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantService {

    @Autowired
    private PaymentService paymentService;

    private final IMerchantRepository iMerchantRepository;
    private final IPaymentSubscriptionRepository iPaymentSubscriptionRepository;

    public MerchantService(IMerchantRepository iMerchantRepository, IPaymentSubscriptionRepository iPaymentSubscriptionRepository) {
        this.iMerchantRepository = iMerchantRepository;
        this.iPaymentSubscriptionRepository = iPaymentSubscriptionRepository;
    }

    public MerchantDto getMerchant(String businessEmail){
        Merchant merchant = iMerchantRepository.findByBusinessEmail(businessEmail);
        if(merchant == null) throw new IllegalArgumentException("Merchant with the provided email does not exist.");
        return MerchantConverter.convertToDto(merchant);
    }

    public List<PaymentMethodInfoDto> getSubscribedPayments(String businessEmail) {
        Merchant merchant = iMerchantRepository.findByBusinessEmail(businessEmail);
        if (merchant == null) throw new IllegalArgumentException("Merchant with the provided email does not exist.");

        List<PaymentSubscription> subscriptions = iPaymentSubscriptionRepository.findByMerchantId(merchant.getId());
        if (subscriptions == null || subscriptions.isEmpty()) throw new IllegalStateException("No subscriptions found for the merchant.");

        List<PaymentMethod> paymentMethods = paymentService.readPaymentMethods();
        if (paymentMethods == null || paymentMethods.isEmpty()) throw new IllegalStateException("No payment methods available.");

        List<PaymentMethodInfoDto> dtos = new ArrayList<>();
        for (PaymentSubscription subscription : subscriptions) {
            PaymentMethod method = paymentMethods.stream()
                    .filter(paymentMethod -> paymentMethod.getId().equals(subscription.getPaymentMethodId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Payment method not found for subscription ID: " + subscription.getPaymentMethodId())
                    );
            dtos.add(PaymentMethodConverter.convertToDto(method));
        }
        return dtos;
    }
}
