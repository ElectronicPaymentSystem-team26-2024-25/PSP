package com.psp.psp.controller;

import com.psp.psp.dto.payments.PaymentMethodDto;
import com.psp.psp.service.PaymentManagementService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/paymentManagement", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentManagementController {

    @Autowired
    private PaymentManagementService paymentService;

    @GetMapping("")
    public ResponseEntity<List<PaymentMethodDto>> getMethods(HttpServletResponse response){
        return paymentService.getPaymentMethods();
    }

    @PostMapping("create")
    public ResponseEntity<PaymentMethodDto> createMethod(@RequestBody PaymentMethodDto paymentDto,
                                                         HttpServletResponse response){
        return paymentService.createMethod(paymentDto);
    }

    @DeleteMapping("delete/{paymentId}")
    public ResponseEntity<Void> deleteMethod(@PathVariable("paymentId") Long paymentId,
                                 HttpServletResponse response){
        if(paymentService.deleteMethod(paymentId)) return ResponseEntity.ok().build();
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("update")
    public ResponseEntity<PaymentMethodDto> updateMethod(@RequestBody PaymentMethodDto paymentDto,
                                                               HttpServletResponse response){
        return paymentService.updateMethod(paymentDto);
    }
}
