package com.psp.psp.controller;

import com.psp.psp.dto.MerchantDto;
import com.psp.psp.dto.PaymentMethodInfoDto;
import com.psp.psp.service.MerchantService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<PaymentMethodInfoDto>> getSubscribedPayments(
            @RequestParam String businessEmail, HttpServletResponse response){
        return ResponseEntity.ok(merchantService.getSubscribedPayments(businessEmail));
    }
}
