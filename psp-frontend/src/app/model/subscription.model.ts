export interface Subscriptions{
    merchantEmail: string;
    paymentMethods: PaymentMethodInfo[];
}

export interface Subscription{
    paymentMethodName: string;
    merchantId: number;
    paymentMethodId: number;
    active: boolean
}

export interface PaymentMethodInfo{
    name: string;
    type: string;
}

export enum PaymentType{
    bank,
    wallet,
    crypto
}

export function PaymentTypeToString(type: PaymentType): string {
    switch (type) {
        case PaymentType.bank:
            return "Bank";
        case PaymentType.wallet:
            return "Wallet";
        case PaymentType.crypto:
            return "Crypto";
        default:
            throw new Error("Invalid PaymentType");
    }
}

export function StringToPaymentType(type: string): PaymentType {
    switch (type) {
        case "Bank":
            return PaymentType.bank;
        case "Wallet":
            return PaymentType.wallet;
        case "Crypto":
            return PaymentType.crypto;
        default:
            throw new Error("Invalid string for PaymentType");
    }
}