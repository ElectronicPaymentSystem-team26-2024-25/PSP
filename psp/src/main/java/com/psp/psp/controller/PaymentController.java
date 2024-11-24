package com.psp.psp.controller;

import com.psp.psp.dto.PaymentRequest;
import com.psp.psp.dto.PaymentResponse;
import com.psp.psp.dto.SubscriptionDto;
import com.psp.psp.service.MerchantService;
import com.psp.psp.service.PaymentService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RestTemplate restTemplate;


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
    @PostMapping("/execute-payment")
    public ResponseEntity<PaymentResponse> executePayment(@RequestBody PaymentRequest request){
        String url = "http://localhost:8095/"+request.getPath();
        ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(url, request, PaymentResponse.class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
}
