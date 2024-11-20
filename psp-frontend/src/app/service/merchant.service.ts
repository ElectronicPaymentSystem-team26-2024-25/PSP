import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Merchant } from '../model/merchant.model';
import { environment } from '../env/environment';
import { PaymentMethodInfo } from '../model/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class MerchantService {

  constructor(private http: HttpClient) { }

  getMerchant(email: string): Observable<Merchant> {
    return this.http.get<Merchant>(environment.apiHost + 'merchant?businessEmail=' + email);
  }

  getSubscribedPayments(email: string): Observable<PaymentMethodInfo[]>{
    return this.http.get<PaymentMethodInfo[]>(environment.apiHost + 'merchant/payment/subscribed?businessEmail=' + email);
  }

  getNotSubscribedPayments(email: string): Observable<PaymentMethodInfo[]>{
    return this.http.get<PaymentMethodInfo[]>(environment.apiHost + 'merchant/payment/not/subscribed?businessEmail=' + email);
  }
}