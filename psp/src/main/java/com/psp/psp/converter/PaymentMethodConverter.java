package com.psp.psp.converter;

import com.psp.psp.dto.payments.PaymentMethodDto;
import com.psp.psp.model.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodConverter {

    public static PaymentMethodDto convertToDto(PaymentMethod method){
        PaymentMethodDto dto = new PaymentMethodDto();
        dto.setId(method.getId());
        dto.setName(method.getName());
        dto.setType(method.getType());
        return dto;
    }
}
