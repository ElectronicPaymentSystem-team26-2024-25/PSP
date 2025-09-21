import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentType, Subscription, Subscriptions } from '../model/subscription.model';
import { environment } from '../env/environment';
import { PaymentExecutionRequest, PayPalRequest } from '../model/payment-execution-request.model';
import { PaymentApproveLink, PaymentExecutionResponse, PayPalResponse } from '../model/payment-execution-response.model';
import { MerchantOrder } from '../model/merchant-order.model';
import { MerchantInfo } from '../model/merchant-info.model';
import { FailReason } from '../model/fail-reason.model';
import { PayPalClient } from '../payments/model/payments.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  subscribe(subscription: Subscriptions): Observable<Subscriptions> {
    return this.http.post<Subscriptions>(environment.apiHost + 'payment/subscribe', subscription);
  }

  unsubscribe(subscription: Subscription): Observable<void> {
    return this.http.delete<void>(environment.apiHost + 'payment/unsubscribe/' + subscription.merchantId + "/" + subscription.paymentMethodId);
  }

  createPayPalClient(client: PayPalClient, methodId: number): Observable<PayPalClient> {
    return this.http.post<PayPalClient>(environment.apiHost + 'payment/client/' + methodId, client)
  }

  getMerchantsSubscribed(merchantPassword: string): Observable<Subscriptions>{
    return this.http.get<Subscriptions>(environment.apiHost + "payment/merchant/subscribed?merchantPassword=" + merchantPassword)
  }
  sendBankPaymentRequest(paymentRequest: PaymentExecutionRequest, bankPort: string): Observable<PaymentExecutionResponse>{
    return this.http.post<PaymentExecutionResponse>(environment.apiHost + "payment/execute-payment", paymentRequest)
  }

  sendPayPalRequest(request: PayPalRequest): Observable<PaymentApproveLink>{
    return this.http.post<PaymentApproveLink>(environment.apiHost + "payment/process-payment/" + PaymentType.wallet, request)
  }

  capturePayPalPayment(orderId: string): Observable<PayPalResponse> {
    return this.http.get<PayPalResponse>(environment.apiHost + "payment/capture-payment/" + orderId);
  }

  getOrder(orderLink: string): Observable<MerchantOrder>{
    return this.http.get<MerchantOrder>(environment.apiHost + "payment/order/"+orderLink)
  }
  getMerchantInfo(merchantId: string): Observable<MerchantInfo>{
    return this.http.get<MerchantInfo>(environment.apiHost + "payment/merchant/"+merchantId)
  }
  getFailReason(orderId: string): Observable<FailReason>{
    return this.http.get<FailReason>(environment.apiHost + "payment/fail/"+orderId)
  }
}
