package com.psp.psp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psp.psp.dto.*;
import com.psp.psp.model.*;
import com.psp.psp.repository.interfaces.IMerchantBankRepository;
import com.psp.psp.repository.interfaces.IMerchantOrderRepository;
import com.psp.psp.repository.interfaces.IMerchantRepository;
import com.psp.psp.repository.interfaces.IPaymentSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private static final String paymentsFilePath = "./configuration/paymentMethods.json";

    private final IMerchantRepository iMerchantRepository;
    private final IPaymentSubscriptionRepository iPaymentSubscriptionRepository;
    @Autowired
    IMerchantOrderRepository iMerchantOrderRepository;
    @Autowired
    IMerchantBankRepository iMerchantBankRepository;
    public PaymentService(IMerchantRepository iMerchantRepository, IPaymentSubscriptionRepository iPaymentSubscriptionRepository) {
        this.iMerchantRepository = iMerchantRepository;
        this.iPaymentSubscriptionRepository = iPaymentSubscriptionRepository;
    }

    public List<PaymentMethod> readPaymentMethods(){
        List<PaymentMethod> methods = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            methods = objectMapper.readValue(
                    new File(paymentsFilePath),
                    new TypeReference<List<PaymentMethod>>() {}
            );
        } catch (IOException e) {
            System.err.println("Error reading payment methods: " + e.getMessage());
        }
        return methods;
    }

    public SubscriptionDto subscribe(SubscriptionDto subscriptionDto){
        if(subscriptionDto.getPaymentMethods().size() < 1) throw new IllegalStateException("Must be at least one payment method to subscribe to.");
        Merchant merchant = iMerchantRepository.findByBusinessEmail(subscriptionDto.getMerchantEmail());
        if(merchant == null) throw new IllegalArgumentException("Merchant with the provided email does not exist.");

        List<PaymentMethod> paymentMethods = readPaymentMethods();
        subscriptionDto.getPaymentMethods().forEach(subscription ->{
            PaymentMethod method = paymentMethods.stream()
                    .filter(paymentMethod -> paymentMethod.getName().equals(subscription.getName()) && paymentMethod.getType().equals(subscription.getType()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Payment method not found for subscription NAME: " + subscription.getName())
                    );
            PaymentSubscription paymentSubscription = new PaymentSubscription(merchant.getId(), method.getId());
            iPaymentSubscriptionRepository.save(paymentSubscription);
        });
        return subscriptionDto;
    }
    public MerchantOrder saveOrder(CreateOrderRequest orderRequest){
        Merchant merchant = iMerchantRepository.findByMerchantPassword(UUID.fromString(orderRequest.getMerchantPassword()));
        if(merchant == null)
            return null;
        MerchantOrder order = new MerchantOrder(orderRequest.getMerchantOrderId(), LocalDateTime.now(), orderRequest.getAmount(), orderRequest.getMerchantId(),
                ".", PaymentStatus.IN_PROGRESS);
        order.setLinkUUID(UUID.randomUUID());
        order.setFailReason(".");
        System.out.println(order);
        return iMerchantOrderRepository.save(order);
    }
    public CreateOrderResponse generatePSPPaymentLink(String merchantPassword, UUID linkUUID){
        CreateOrderResponse response = new CreateOrderResponse();
        response.setUrl("http://localhost:4200/pay.psp?merchant="+merchantPassword+"&order="+linkUUID.toString());
        return response;
    }
    public MerchantOrder getOrder(String linkUUID){
        return iMerchantOrderRepository.findByLinkUUID(UUID.fromString(linkUUID));
    }
    public OrderStatusDto saveOrderStatus(PaymentStatusResponse bankResponse){
        MerchantOrder order = iMerchantOrderRepository.getReferenceById(bankResponse.getMerchantOrderId());
        order.setOrderStatus(bankResponse.getStatus());
        order.setPaymentId(bankResponse.getPaymentId());
        order.setFailReason(bankResponse.getFailReason());
        iMerchantOrderRepository.save(order);
        OrderStatusDto orderDTO = new OrderStatusDto();
        orderDTO.setOrderId(bankResponse.getMerchantOrderId());
        orderDTO.setOrderStatus(bankResponse.getStatus().toString());
        return orderDTO;
    }
    public MerchantInfoDto getMerchantInfo(String merchantId){
        MerchantInfoDto dto = new MerchantInfoDto();
        MerchantBank merchantBank = iMerchantBankRepository.findByMerchantId(UUID.fromString(merchantId));
        dto.setPort(merchantBank.getPort());
        dto.setMerchantPassword(merchantBank.getMerchantPassword().toString());
        return dto;
    }
    public FailReasonDto getFailReason(int orderId){
        MerchantOrder order = iMerchantOrderRepository.getReferenceById(orderId);
        FailReasonDto reason = new FailReasonDto();
        reason.setFailReason(order.getFailReason());
        return reason;
    }
}
