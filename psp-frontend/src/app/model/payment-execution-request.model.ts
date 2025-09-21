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

export interface PayPalRequest{
    merchantId: string,
    amount: string,
    merchantOrderId: string,
    merchantTimestamp: Date,
    sucessUrl: string,
    errorUrl: string,
    failedUrl: string,
    brandName: string,
}