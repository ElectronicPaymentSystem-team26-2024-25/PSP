package com.psp.psp.service;

import com.psp.psp.repository.interfaces.IPaymentManagementRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentManagementService {

    private final IPaymentManagementRepository repository;

    public PaymentManagementService(IPaymentManagementRepository repository) {
        this.repository = repository;
    }
}
