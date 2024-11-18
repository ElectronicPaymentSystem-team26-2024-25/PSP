import { Component, OnInit } from '@angular/core';
import { MerchantService } from '../service/merchant.service';
import { AuthServiceService } from '../auth/auth-service.service';
import { Merchant } from '../model/merchant.model';
import { User } from '../model/user.model';

@Component({
  selector: 'app-merchant-page',
  templateUrl: './merchant-page.component.html',
  styleUrls: ['./merchant-page.component.css']
})
export class MerchantPageComponent implements OnInit{
  
  user: User = {
    id: 0,
    email: "",
    type: ""
  }

  merchant: Merchant = {
    merchantId: '',
    merchantPassword: '',
    businessEmail: '',
    businessName: '',
    legalName: '',
    legalLastname: ''
  }

  constructor(private service: MerchantService, private auth: AuthServiceService){}
  
  
  
  ngOnInit(): void {
    this.auth.user$.subscribe(u => {
        this.user = u;
    });
    this.service.getMerchant(this.user.email).subscribe({
      next: (response: Merchant) => {
        if(response == null) return;
        this.merchant = response;
      }
    })
  }
}
