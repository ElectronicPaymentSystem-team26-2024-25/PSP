package com.psp.psp.converter;

import com.psp.psp.dto.payments.PaymentMethodDto;
import com.psp.psp.model.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentMethodConverter {

    public static PaymentMethod convertFromDto(PaymentMethodDto dto){
        PaymentMethod method = new PaymentMethod(dto.getId(), dto.getName(), dto.getType(), dto.isActive());
        return method;
    }

    public static PaymentMethodDto convertToDto(PaymentMethod method){
        PaymentMethodDto dto = new PaymentMethodDto();
        dto.setId(method.getId());
        dto.setName(method.getName());
        dto.setType(method.getType());
        dto.setActive(method.isActive());
        return dto;
    }

    public static List<PaymentMethodDto> convertToDto(List<PaymentMethod> methods){
        List<PaymentMethodDto> methodDtos = new ArrayList<>();
        methods.forEach(method -> {
            PaymentMethodDto dto = convertToDto(method);
                    methodDtos.add(dto);
        });
        return methodDtos;
    }
}
