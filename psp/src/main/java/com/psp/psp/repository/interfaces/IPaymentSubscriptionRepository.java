package com.psp.psp.repository.interfaces;

import com.psp.psp.model.PaymentSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaymentSubscriptionRepository extends JpaRepository<PaymentSubscription, Long> {
    public List<PaymentSubscription> findByMerchantId(Long merchantId);
    public PaymentSubscription save(PaymentSubscription paymentSubscription);
}
