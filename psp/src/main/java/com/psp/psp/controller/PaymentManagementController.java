package com.psp.psp.controller;

import com.psp.psp.dto.payments.PaymentMethodDto;
import com.psp.psp.model.PaymentMethod;
import com.psp.psp.service.PaymentManagementService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/paymentManagement", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentManagementController {

    @Autowired
    private PaymentManagementService paymentService;

    @GetMapping("")
    public ResponseEntity<PaymentMethodDto> getMethods(HttpServletResponse response){
        throw new UnsupportedOperationException();
    }

    @PostMapping("create")
    public ResponseEntity<PaymentMethodDto> createMethod(@RequestBody PaymentMethodDto paymentDto,
                                                         HttpServletResponse response){
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("delete")
    public ResponseEntity<PaymentMethodDto> deleteMethod(@RequestBody PaymentMethodDto paymentDto,
                                                         HttpServletResponse response){
        throw new UnsupportedOperationException();
    }

    @PatchMapping("update")
    public ResponseEntity<PaymentMethodDto> updateMethod(@RequestBody PaymentMethodDto paymentDto,
                                                         HttpServletResponse response){
        throw new UnsupportedOperationException();
    }
}
