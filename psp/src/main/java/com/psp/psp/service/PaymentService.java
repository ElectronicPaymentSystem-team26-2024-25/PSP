package com.psp.psp.service;

import com.psp.psp.dto.merchant.MerchantInfoDto;
import com.psp.psp.dto.orders.CreateOrderRequest;
import com.psp.psp.dto.orders.CreateOrderResponse;
import com.psp.psp.dto.orders.FailReasonDto;
import com.psp.psp.dto.orders.OrderStatusDto;
import com.psp.psp.dto.payments.PaymentStatusResponse;
import com.psp.psp.dto.subscriptions.SubscriptionsDto;
import com.psp.psp.enumerations.PaymentStatus;
import com.psp.psp.model.*;
import com.psp.psp.repository.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final IMerchantRepository iMerchantRepository;
    private final IPaymentSubscriptionRepository iPaymentSubscriptionRepository;
    private final IPaymentManagementRepository iPaymentManagementRepository;

    @Autowired
    IMerchantOrderRepository iMerchantOrderRepository;
    @Autowired
    IMerchantBankRepository iMerchantBankRepository;
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    public PaymentService(IMerchantRepository iMerchantRepository, IPaymentSubscriptionRepository iPaymentSubscriptionRepository, IPaymentManagementRepository iPaymentManagementRepository) {
        this.iMerchantRepository = iMerchantRepository;
        this.iPaymentSubscriptionRepository = iPaymentSubscriptionRepository;
        this.iPaymentManagementRepository = iPaymentManagementRepository;
    }

    public boolean unsubscribe(Long merchantId, Long paymentMethodId){
        Subscription subscription = iPaymentSubscriptionRepository.find(merchantId, paymentMethodId);
        if(subscription == null) return false;
        iPaymentSubscriptionRepository.delete(subscription);
        return true;
    }

    public SubscriptionsDto subscribe(SubscriptionsDto subscriptionsDto){
        if(subscriptionsDto.getPaymentMethods().isEmpty()) throw new IllegalStateException("Must be at least one payment method to subscribe to.");
        Merchant merchant = iMerchantRepository.findByBusinessEmail(subscriptionsDto.getMerchantEmail());
        if(merchant == null) throw new IllegalArgumentException("Merchant with the provided email does not exist.");

        List<PaymentMethod> paymentMethods = iPaymentManagementRepository.findAll();
        subscriptionsDto.getPaymentMethods().forEach(subscription ->{
            PaymentMethod method = paymentMethods.stream()
                    .filter(paymentMethod -> paymentMethod.getName().equals(subscription.getName()) && paymentMethod.getType().equals(subscription.getType()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Payment method not found for subscription NAME: " + subscription.getName())
                    );
            Subscription paymentSubscription = new Subscription(merchant.getId(), method.getId());
            log.info("Merchant {} is subscribed to payment method with id {}",
                    subscriptionsDto.getMerchantEmail(), paymentSubscription.getPaymentMethodId());
            iPaymentSubscriptionRepository.save(paymentSubscription);
        });
        return subscriptionsDto;
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
        response.setUrl("https://localhost:4200/pay.psp?merchant="+merchantPassword+"&order="+linkUUID.toString());
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
        log.warn("Order {} finished with status {}",
                order.getMerchantOrderId(), order.getOrderStatus());
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
        log.warn("Order {} failed due to {}",
                orderId, reason.getFailReason());
        return reason;
    }

    public String getPaymentServiceLink(Long paymentMethodId){
        PaymentMethod paymentMethod = iPaymentManagementRepository.getReferenceById(paymentMethodId);
        return paymentMethod.getEndpoint();
    }

    public String getPaymentServiceAddress(Long paymentMethodId){
        PaymentMethod paymentMethod = iPaymentManagementRepository.getReferenceById(paymentMethodId);
        return paymentMethod.getAddress();
    }
}