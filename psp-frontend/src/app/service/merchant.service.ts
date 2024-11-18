import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Merchant } from '../model/merchant.model';
import { environment } from '../env/environment';

@Injectable({
  providedIn: 'root'
})
export class MerchantService {

  constructor(private http: HttpClient) { }

  getMerchant(email: string): Observable<Merchant> {
    return this.http.get<Merchant>(environment.apiHost + 'merchant?businessEmail=' + email);
  }
}