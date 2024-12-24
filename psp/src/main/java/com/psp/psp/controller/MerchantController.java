package com.psp.psp.controller;

import com.psp.psp.dto.merchant.MerchantDto;
import com.psp.psp.dto.payments.PaymentMethodDto;
import com.psp.psp.dto.subscriptions.SubscriptionDto;
import com.psp.psp.service.MerchantService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/merchant", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantController {

    @Autowired
    private MerchantService merchantService;


    @GetMapping("")
    public ResponseEntity<MerchantDto> getMerchant(
            @RequestParam String businessEmail, HttpServletResponse response){
        MerchantDto responseDto = merchantService.getMerchant(businessEmail);
        if(responseDto == null) return (ResponseEntity<MerchantDto>) ResponseEntity.notFound();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/payment/subscribed")
    public ResponseEntity<List<SubscriptionDto>> getSubscribedPayments(
            @RequestParam String businessEmail, HttpServletResponse response){
        return ResponseEntity.ok(merchantService.getSubscribedPaymentMethods(businessEmail));
    }

    @GetMapping("/payment/not/subscribed")
    public ResponseEntity<List<PaymentMethodDto>> getNotSubscribedPayments(
            @RequestParam String businessEmail, HttpServletResponse response){
        return ResponseEntity.ok(merchantService.getNotSubscribedPaymentMethods(businessEmail));
    }

    @PatchMapping("/payment/change-status")
    public ResponseEntity<SubscriptionDto> changeStatus(@RequestBody SubscriptionDto subscription, HttpServletResponse response){
        return ResponseEntity.ok(merchantService.changeStatus(subscription));
    }
}
