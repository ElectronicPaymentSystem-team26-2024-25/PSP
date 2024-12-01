package com.psp.psp.converter;

import com.psp.psp.dto.SubscriptionDto;
import com.psp.psp.model.PaymentMethod;
import com.psp.psp.model.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SubscriptionConverter {

    public static SubscriptionDto toDto(Subscription subscription, String paymentName){
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setPaymentMethodName(paymentName);
        subscriptionDto.setMerchantId(subscription.getMerchantId());
        subscriptionDto.setPaymentMethodId(subscription.getPaymentMethodId());
        subscriptionDto.setActive(subscription.isActive());
        return subscriptionDto;
    }

    public static List<SubscriptionDto> toDto(List<Subscription> subscriptions, List<PaymentMethod> paymentMethods){
        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        subscriptions.forEach(subscription -> {
            String paymentMethodName = paymentMethods.stream()
                    .filter(method -> Objects.equals(method.getId(), subscription.getPaymentMethodId()))
                    .findFirst()
                    .get()
                    .getName();
            subscriptionDtos.add(SubscriptionConverter.toDto(subscription, paymentMethodName));
        });
        return subscriptionDtos;
    }
}