package com.psp.psp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psp.psp.model.PaymentMethod;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private static final String paymentsFilePath = "./configuration/paymentMethods.json";

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
}
