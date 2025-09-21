package com.psp.psp.config.payments.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psp.psp.config.payments.model.PaymentRegistry;
import com.psp.psp.model.PaymentMethod;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentConfigurationService {
    private static final String paymentsFilePath = "./configuration/paymentMethods.json";
    private static final String paymentsRegistryFilePath = "./configuration/paymentRegistry.json";

    public static List<PaymentMethod> readPaymentMethods(){
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

    public static List<PaymentRegistry> readPaymentRegistry(){
        List<PaymentRegistry> methods = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            methods = objectMapper.readValue(
                    new File(paymentsRegistryFilePath),
                    new TypeReference<List<PaymentRegistry>>() {}
            );
        } catch (IOException e) {
            System.err.println("Error reading payment methods: " + e.getMessage());
        }
        return methods;
    }

    public static PaymentMethod getPaymentMethod(Long id){
        List<PaymentMethod> methods = readPaymentMethods();
        if(methods.isEmpty()) return null;
        return methods.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                "Payment method not found for ID: " + id)
        );
    }

    public static PaymentRegistry getPaymentRegistry(Long id){
        List<PaymentRegistry> methods = readPaymentRegistry();
        if(methods.isEmpty()) return null;
        return methods.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                        "Payment registry not found for ID: " + id)
                );
    }
}