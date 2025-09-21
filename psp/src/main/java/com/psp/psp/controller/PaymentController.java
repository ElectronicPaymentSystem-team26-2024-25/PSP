package com.psp.psp.controller;

import com.psp.psp.dto.merchant.MerchantInfoDto;
import com.psp.psp.dto.merchant.PayPalClientDto;
import com.psp.psp.dto.orders.CreateOrderRequest;
import com.psp.psp.dto.orders.CreateOrderResponse;
import com.psp.psp.dto.orders.FailReasonDto;
import com.psp.psp.dto.orders.OrderStatusDto;
import com.psp.psp.dto.payments.PaymentApproveLink;
import com.psp.psp.dto.payments.PaymentRequest;
import com.psp.psp.dto.payments.PaymentResponse;
import com.psp.psp.dto.payments.PaymentStatusResponse;
import com.psp.psp.dto.subscriptions.SubscriptionsDto;
import com.psp.psp.model.MerchantOrder;
import com.psp.psp.service.MerchantService;
import com.psp.psp.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    @Qualifier("plainRestTemplate")
    private RestTemplate plainRestTemplate;

    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionsDto> subscribe(
            @RequestBody SubscriptionsDto subscriptionsDto, HttpServletResponse response) {
        return ResponseEntity.ok(paymentService.subscribe(subscriptionsDto));
    }

    @DeleteMapping("/unsubscribe/{merchantId}/{paymentMethodId}")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable("merchantId") Long merchantId, @PathVariable("paymentMethodId") Long paymentMethodId,HttpServletResponse response) {
        if(paymentService.unsubscribe(merchantId, paymentMethodId)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/merchant/subscribed")
    public ResponseEntity<SubscriptionsDto> getSubscribedByMerchant(
            @RequestParam String merchantPassword, HttpServletResponse response){
        return ResponseEntity.ok(merchantService.getMerchantsPaymentMethods(merchantPassword));
    }

    @PostMapping(path = "/process-payment/{paymentMethodId}")
    public ResponseEntity<PaymentApproveLink> processPaypalPayment(@PathVariable("paymentMethodId") Long paymentMethodId, @RequestBody PaymentRequest paymentRequest){
        String endpointUrl = paymentService.getPaymentServiceLink(paymentMethodId);
        String serviceAddress = paymentService.getPaymentServiceAddress(paymentMethodId);
        ResponseEntity<PaymentApproveLink> serviceResponse = restTemplate.postForEntity("https://PAYPAL-SERVICE/api/paypal/payment", paymentRequest, PaymentApproveLink.class);
        return new ResponseEntity<>(serviceResponse.getBody(), serviceResponse.getStatusCode());
    }

    @PostMapping("/execute-payment")
    public ResponseEntity<PaymentResponse> executePayment(@RequestBody PaymentRequest request){
        String url = "https://Bank1/api/cardpayment/cardpaymentform";
        ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(url, request, PaymentResponse.class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    @PostMapping("/order-status")
    public ResponseEntity<PaymentResponse> updateOrderStatus(@RequestBody PaymentStatusResponse bankResponse){
        String url = "https://localhost:8075/api/orders/order-status";
        OrderStatusDto orderStatusDto = paymentService.saveOrderStatus(bankResponse);
        ResponseEntity<Object> response = plainRestTemplate.postForEntity(url, orderStatusDto, Object.class);
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

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<MerchantInfoDto> getMerchantInfo(@PathVariable String merchantId){
        MerchantInfoDto info = paymentService.getMerchantInfo(merchantId);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
    @GetMapping("/fail/{orderId}")
    public ResponseEntity<FailReasonDto> getFailReason(@PathVariable String orderId){
        FailReasonDto dto = paymentService.getFailReason(Integer.parseInt(orderId));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
