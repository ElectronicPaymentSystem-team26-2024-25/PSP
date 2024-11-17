package com.psp.psp.repository.interfaces;

import com.psp.psp.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, Long> {
}
