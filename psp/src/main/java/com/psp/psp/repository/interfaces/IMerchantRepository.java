package com.psp.psp.repository.interfaces;

import com.psp.psp.model.Merchant;
import com.psp.psp.model.PaymentSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, Long> {
    public Merchant findByBusinessEmail(String businessEmail);
}
