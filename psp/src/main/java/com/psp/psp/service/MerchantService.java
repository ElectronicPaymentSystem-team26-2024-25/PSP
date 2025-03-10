package com.psp.psp.service;

import com.psp.psp.config.payments.service.PaymentConfigurationService;
import com.psp.psp.converter.MerchantConverter;
import com.psp.psp.converter.PaymentMethodConverter;
import com.psp.psp.converter.SubscriptionConverter;
import com.psp.psp.dto.merchant.MerchantDto;
import com.psp.psp.dto.payments.PaymentMethodDto;
import com.psp.psp.dto.subscriptions.SubscriptionDto;
import com.psp.psp.dto.subscriptions.SubscriptionsDto;
import com.psp.psp.model.Merchant;
import com.psp.psp.model.PaymentMethod;
import com.psp.psp.model.Subscription;
import com.psp.psp.repository.interfaces.IMerchantRepository;
import com.psp.psp.repository.interfaces.IPaymentSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public SubscriptionDto changeStatus(SubscriptionDto subscriptionDto){
        Subscription subscription = iPaymentSubscriptionRepository
                .find(subscriptionDto.getMerchantId(), subscriptionDto.getPaymentMethodId());
        if(subscription == null) throw new IllegalArgumentException("Subscription not found.");
        subscription.setActive(!subscription.isActive());
        iPaymentSubscriptionRepository.save(subscription);
        return SubscriptionConverter.toDto(subscription, subscriptionDto.getPaymentMethodName());
    }

    public MerchantDto getMerchant(String businessEmail){
        Merchant merchant = iMerchantRepository.findByBusinessEmail(businessEmail);
        if(merchant == null) throw new IllegalArgumentException("Merchant with the provided email does not exist.");
        return MerchantConverter.convertToDto(merchant);
    }

    public List<SubscriptionDto> getSubscribedPaymentMethods(String businessEmail) {
        Merchant merchant = iMerchantRepository.findByBusinessEmail(businessEmail);
        if (merchant == null) throw new IllegalArgumentException("Merchant with the provided email does not exist.");
        List<Subscription> subscriptions = iPaymentSubscriptionRepository.findByMerchantId(merchant.getId());
        if (subscriptions == null || subscriptions.isEmpty()) throw new IllegalStateException("No subscriptions found for the merchant.");
        return SubscriptionConverter.toDto(subscriptions, getPaymentMethods(merchant, true));
    }

    public List<PaymentMethodDto> getNotSubscribedPaymentMethods(String businessEmail) {
        Merchant merchant = iMerchantRepository.findByBusinessEmail(businessEmail);
        if (merchant == null) throw new IllegalArgumentException("Merchant with the provided email does not exist.");
        return getPaymentMethodsDto(merchant, false);
    }

    public SubscriptionsDto getMerchantsPaymentMethods(String merchantPassword) {
        Merchant merchant = iMerchantRepository.findByMerchantPassword(UUID.fromString(merchantPassword));
        if (merchant == null) throw new IllegalArgumentException("Merchant with the provided MERCHANT_PASSWORD does not exist.");
        SubscriptionsDto subscriptionsDto = new SubscriptionsDto();
        subscriptionsDto.setMerchantEmail(merchant.getBusinessEmail());
        subscriptionsDto.setPaymentMethods(getPaymentMethodsDto(merchant, true));
        return subscriptionsDto;
    }

    private List<PaymentMethodDto> getPaymentMethodsDto(Merchant merchant, boolean isSubscribed){
        return getPaymentMethods(merchant, isSubscribed).stream()
                .map(PaymentMethodConverter::convertToDto)
                .collect(Collectors.toList());
    }

    private List<PaymentMethod> getPaymentMethods(Merchant merchant, boolean isSubscribed) {
        List<Subscription> subscriptions = iPaymentSubscriptionRepository.findByMerchantId(merchant.getId());
        if (subscriptions == null || subscriptions.isEmpty()) throw new IllegalStateException("No subscriptions found for the merchant.");
        List<PaymentMethod> paymentMethods = PaymentConfigurationService.readPaymentMethods();
        if (paymentMethods == null || paymentMethods.isEmpty()) throw new IllegalStateException("No payment methods available.");

        Set<Long> subscribedMethodIds = subscriptions.stream()
                .map(Subscription::getPaymentMethodId)
                .collect(Collectors.toSet());
        return paymentMethods.stream()
                .filter(paymentMethod -> isSubscribed == subscribedMethodIds.contains(paymentMethod.getId()))
                .collect(Collectors.toList());
    }
}