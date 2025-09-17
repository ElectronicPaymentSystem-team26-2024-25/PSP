package com.psp.psp.service;

import com.psp.psp.converter.PaymentMethodConverter;
import com.psp.psp.dto.payments.PaymentMethodDto;
import com.psp.psp.model.PaymentMethod;
import com.psp.psp.repository.interfaces.IPaymentManagementRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentManagementService {

    private final IPaymentManagementRepository repository;

    public PaymentManagementService(IPaymentManagementRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> deleteMethod(Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Payment method deleted successfully.");
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Payment method with id " + id + " not found.");
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error: " + e.getMessage());
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