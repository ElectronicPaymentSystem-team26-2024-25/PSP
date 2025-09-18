import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentMethod } from '../model/subscription.model';
import { environment } from '../env/environment';

@Injectable({
  providedIn: 'root'
})
export class AdministrationService {

  constructor(private http: HttpClient) { }

  getMethods(): Observable<PaymentMethod[]> {
    return this.http.get<PaymentMethod[]>(environment.apiHost + 'paymentManagement');
  }

  createMethod(paymentMethod: PaymentMethod): Observable<PaymentMethod> {
    return this.http.post<PaymentMethod>(environment.apiHost + 'paymentManagement/create', paymentMethod);
  }

  updateMethod(paymentMethod: PaymentMethod): Observable<PaymentMethod> {
    return this.http.put<PaymentMethod>(environment.apiHost + 'paymentManagement/update', paymentMethod);
  }

  deleteMethod(paymentMethod: PaymentMethod): Observable<void> {
    return this.http.delete<void>(environment.apiHost + 'paymentManagement/delete/' + paymentMethod.id);
  }

}