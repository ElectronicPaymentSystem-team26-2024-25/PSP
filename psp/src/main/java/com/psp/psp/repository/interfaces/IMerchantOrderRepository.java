package com.psp.psp.repository.interfaces;

import com.psp.psp.model.MerchantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface IMerchantOrderRepository extends JpaRepository<MerchantOrder, Integer> {
    public MerchantOrder findByLinkUUID(UUID linkUUID);
}
