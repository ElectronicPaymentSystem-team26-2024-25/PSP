export interface PaymentExecutionRequest{
    merchantId: string,
    merchantPassword: string,
    amount: number,
    merchantOrderId: number,
    merchantTimestamp: Date,
    successUrl: string,
    errorUrl: string,
    failedUrl: string,
    path: string
}