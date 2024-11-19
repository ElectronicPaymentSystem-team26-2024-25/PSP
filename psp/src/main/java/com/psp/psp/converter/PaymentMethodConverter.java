package com.psp.psp.converter;

import com.psp.psp.dto.PaymentMethodInfoDto;
import com.psp.psp.model.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodConverter {

    public static PaymentMethodInfoDto convertToDto(PaymentMethod method){
        PaymentMethodInfoDto dto = new PaymentMethodInfoDto();
        dto.setName(method.getName());
        dto.setType(method.getType());
        return dto;
    }
}
