package com.psp.psp.repository.interfaces;

import com.psp.psp.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, Long> {
    public Merchant findByBusinessEmail(String businessEmail);

    @Query("SELECT s FROM Merchant s WHERE s.MERCHANT_PASSWORD=?1")
    public Merchant findByMerchantPassword(UUID MERCHANT_PASSWORD);
}
