package com.psp.psp.repository.interfaces;

import com.psp.psp.model.MerchantBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMerchantBankRepository extends JpaRepository<MerchantBank, Integer> {
    public MerchantBank findByMerchantId(UUID MerchantId);
}
