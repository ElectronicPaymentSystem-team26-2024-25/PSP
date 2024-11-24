import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscription } from '../model/subscription.model';
import { environment } from '../env/environment';

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
}
