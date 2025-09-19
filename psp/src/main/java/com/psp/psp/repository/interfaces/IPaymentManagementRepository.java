package com.psp.psp.repository.interfaces;

import com.psp.psp.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentManagementRepository extends JpaRepository<PaymentMethod, Long> {
}
