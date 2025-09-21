export interface PaymentExecutionResponse{
    paymentId: string,
    paymentUrl: string
}

export interface PaymentApproveLink {
    message: string;
}

export interface PayPalResponse{
    merchantOrderId: string,
    acquirerOrderId: string,
    acquirerTimestamp: Date,
    paymentId: number,
    redirectUrl: string,
    failReason: string
}