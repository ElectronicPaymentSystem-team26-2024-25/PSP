package com.psp.psp.dto.payments;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PayPalResponseDto {
    private String merchantOrderId;
    private String acquirerOrderId;
    private Date acquirerTimestamp;
    private Long paymentId;
    private String redirectUrl;
    private String failReason;
}
