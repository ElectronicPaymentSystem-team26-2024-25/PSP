package com.psp.psp.controller;

import com.psp.psp.dto.SubscriptionDto;
import com.psp.psp.service.MerchantService;
import com.psp.psp.service.PaymentService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MerchantService merchantService;


    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionDto> subscribe(
            @RequestBody SubscriptionDto subscriptionDto, HttpServletResponse response) {
        return ResponseEntity.ok(paymentService.subscribe(subscriptionDto));
    }

    @GetMapping("/merchant/subscribed")
    public ResponseEntity<SubscriptionDto> getSubscribedByMerchant(
            @RequestParam String merchantPassword, HttpServletResponse response){
        return ResponseEntity.ok(merchantService.getPaymentMethodsByMerchant(merchantPassword));
    }
}
