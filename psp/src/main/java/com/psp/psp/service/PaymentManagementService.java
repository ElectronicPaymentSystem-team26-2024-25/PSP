package com.psp.psp.service;

import com.psp.psp.converter.PaymentMethodConverter;
import com.psp.psp.dto.payments.PaymentMethodDto;
import com.psp.psp.model.PaymentMethod;
import com.psp.psp.model.Subscription;
import com.psp.psp.repository.interfaces.IPaymentManagementRepository;
import com.psp.psp.repository.interfaces.IPaymentSubscriptionRepository;
import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentManagementService {

    private final IPaymentManagementRepository repository;
    private final IPaymentSubscriptionRepository iPaymentSubscriptionRepository;
    private static final Logger log = LoggerFactory.getLogger(PaymentManagementService.class);

    public PaymentManagementService(IPaymentManagementRepository repository, IPaymentSubscriptionRepository iPaymentSubscriptionRepository) {
        this.repository = repository;
        this.iPaymentSubscriptionRepository = iPaymentSubscriptionRepository;
    }


    public boolean deleteMethod(Long id) {
        try {
            //TODO: This should be transaction
            repository.deleteById(id);
            List<Subscription> subscriptionsToDelete = iPaymentSubscriptionRepository.findByPaymentMethodId(id);
            subscriptionsToDelete.forEach(iPaymentSubscriptionRepository::delete);
            log.info("Payment method with ID {} have been deleted",
                    id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<PaymentMethodDto> updateMethod(PaymentMethodDto paymentDto) {
        try {
            if (paymentDto.getId() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            return repository.findById(paymentDto.getId())
                    .map(existing -> {
                        PaymentMethod updated = PaymentMethodConverter.convertFromDto(paymentDto);
                        updated.setId(existing.getId());
                        PaymentMethod saved = repository.save(updated);
                        return ResponseEntity.ok(PaymentMethodConverter.convertToDto(saved));
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethods() {
        List<PaymentMethod> paymentMethods = repository.findAll();
        return ResponseEntity.ok(PaymentMethodConverter.convertToDto(paymentMethods));
    }

    public ResponseEntity<PaymentMethodDto> createMethod(PaymentMethodDto paymentDto) {
        try {
            PaymentMethod method = PaymentMethodConverter.convertFromDto(paymentDto);
            method.setId(null);
            PaymentMethod saved = repository.saveAndFlush(method);
            log.info("Payment method {} created",
                    paymentDto.getName());
            return ResponseEntity.ok(PaymentMethodConverter.convertToDto(saved));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}