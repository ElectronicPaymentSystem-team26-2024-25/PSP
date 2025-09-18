package com.psp.psp.repository.interfaces;

import com.psp.psp.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaymentSubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.merchantId=?1 AND s.paymentMethodId=?2")
    public Subscription find(Long merchantId, Long paymentMethodId);

    public void delete(Subscription subscription);
    public List<Subscription> findByMerchantId(Long merchantId);
    public Subscription save(Subscription paymentSubscription);
    public List<Subscription> findByPaymentMethodId(Long paymentMethodId);
}
