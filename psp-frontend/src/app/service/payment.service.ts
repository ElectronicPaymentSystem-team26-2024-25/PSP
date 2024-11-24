import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscription } from '../model/subscription.model';
import { environment } from '../env/environment';
import { PaymentExecutionRequest } from '../model/payment-execution-request.model';
import { PaymentExecutionResponse } from '../model/payment-execution-response.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  subscribe(subscription: Subscription): Observable<Subscription> {
    return this.http.post<Subscription>(environment.apiHost + 'payment/subscribe', subscription);
  }

  getMerchantsSubscribed(merchantPassword: string): Observable<Subscription>{
    return this.http.get<Subscription>(environment.apiHost + "payment/merchant/subscribed?merchantPassword=" + merchantPassword)
  }
  sendBankPaymentRequest(paymentRequest: PaymentExecutionRequest, bankPort: string): Observable<PaymentExecutionResponse>{
    return this.http.post<PaymentExecutionResponse>(environment.apiHost + "payment/execute-payment", paymentRequest)
  }
}
