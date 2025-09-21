package com.psp.psp.dto.payments;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class PayPalRequestDto {
    private String MerchantId;
    private String Amount;
    private String BrandName;
    private String MerchantOrderId;
    private Date MerchantTimestamp;
    private String SucessUrl;
    private String FailedUrl;
    private String ErrorUrl;
}
