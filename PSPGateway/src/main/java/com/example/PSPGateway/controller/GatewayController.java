package com.example.PSPGateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/cardpayment")
public class GatewayController {
    @Autowired
    RestTemplate restTemplate;
    @PostMapping(consumes = "application/json", path = "/cardpaymentform/{bank}")
    public ResponseEntity<Object> getCardPaymentRequestResponse(@RequestBody Object request, @PathVariable("bank") int bankPort)
    {
        String url = "http://localhost:"+bankPort+"/api/cardpayment/cardpaymentform";
        ResponseEntity<Object> response = restTemplate.postForEntity(url, request, Object.class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
}
