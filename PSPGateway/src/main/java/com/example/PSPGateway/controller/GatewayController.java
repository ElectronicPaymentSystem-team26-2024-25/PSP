package com.example.PSPGateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api")
public class GatewayController {
    @Autowired
    RestTemplate restTemplate;
    @PostMapping(consumes = "application/json", path = "/cardpayment/cardpaymentform/{bank}")
    public ResponseEntity<Object> getCardPaymentRequestResponse(@RequestBody Object request, @PathVariable("bank") int bankPort)
    {
        String url = "https://bank.local:"+bankPort+"/api/cardpayment/cardpaymentform";
        ResponseEntity<Object> response = restTemplate.postForEntity(url, request, Object.class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    @PostMapping(consumes = "application/json", path = "/payment/order-status")
    public ResponseEntity<Object> SendDataToPsp(@RequestBody Object request)
    {
        String url = "https://localhost:8080/payment/order-status";
        ResponseEntity<Object> response = restTemplate.postForEntity(url, request, Object.class);
        return new ResponseEntity<>(response.getStatusCode());
    }
}
