import { PaymentStatus } from "./payment-status";

export interface MerchantOrder{
    merchantOrderId: number,
    merchantTimestamp: Date,
    merchantId: string,
    amount: number,
    paymentId: string,
    orderStatus: PaymentStatus,
    legalName: string,
    legalLastname: string
}