package com.psp.psp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psp.psp.dto.SubscriptionDto;
import com.psp.psp.model.Merchant;
import com.psp.psp.model.PaymentMethod;
import com.psp.psp.model.PaymentSubscription;
import com.psp.psp.repository.interfaces.IMerchantRepository;
import com.psp.psp.repository.interfaces.IPaymentSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

@Service
public class PaymentService {

    private static final String paymentsFilePath = "./configuration/paymentMethods.json";

    private final IMerchantRepository iMerchantRepository;
    private final IPaymentSubscriptionRepository iPaymentSubscriptionRepository;

    public PaymentService(IMerchantRepository iMerchantRepository, IPaymentSubscriptionRepository iPaymentSubscriptionRepository) {
        this.iMerchantRepository = iMerchantRepository;
        this.iPaymentSubscriptionRepository = iPaymentSubscriptionRepository;
    }

    public List<PaymentMethod> readPaymentMethods(){
        List<PaymentMethod> methods = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            methods = objectMapper.readValue(
                    new File(paymentsFilePath),
                    new TypeReference<List<PaymentMethod>>() {}
            );
        } catch (IOException e) {
            System.err.println("Error reading payment methods: " + e.getMessage());
        }
        return methods;
    }

    public SubscriptionDto subscribe(SubscriptionDto subscriptionDto){
        if(subscriptionDto.getPaymentMethods().size() < 1) throw new IllegalStateException("Must be at least one payment method to subscribe to.");
        Merchant merchant = iMerchantRepository.findByBusinessEmail(subscriptionDto.getMerchantEmail());
        if(merchant == null) throw new IllegalArgumentException("Merchant with the provided email does not exist.");

        List<PaymentMethod> paymentMethods = readPaymentMethods();
        subscriptionDto.getPaymentMethods().forEach(subscription ->{
            PaymentMethod method = paymentMethods.stream()
                    .filter(paymentMethod -> paymentMethod.getName().equals(subscription.getName()) && paymentMethod.getType().equals(subscription.getType()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Payment method not found for subscription NAME: " + subscription.getName())
                    );
            PaymentSubscription paymentSubscription = new PaymentSubscription(merchant.getId(), method.getId());
            iPaymentSubscriptionRepository.save(paymentSubscription);
        });
        return subscriptionDto;
    }
}
