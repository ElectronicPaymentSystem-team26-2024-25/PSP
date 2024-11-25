package com.psp.psp.controller;

import com.psp.psp.dto.*;
import com.psp.psp.model.MerchantOrder;
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
    @PostMapping("/order-status")
    public ResponseEntity<PaymentResponse> updateOrderStatus(@RequestBody PaymentStatusResponse bankResponse){
        String url = "http://localhost:8079/api/orders/order-status";
        OrderStatusDto orderStatusDto = paymentService.saveOrderStatus(bankResponse);
        ResponseEntity<Object> response = restTemplate.postForEntity(url, orderStatusDto, Object.class);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest orderRequest){
        MerchantOrder order = paymentService.saveOrder(orderRequest);
        if(order == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        CreateOrderResponse response = paymentService.generatePSPPaymentLink(orderRequest.getMerchantPassword(), order.getLinkUUID());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<MerchantOrder> getOrder(@PathVariable String orderId){
        MerchantOrder order = paymentService.getOrder(orderId);
        if(order == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
