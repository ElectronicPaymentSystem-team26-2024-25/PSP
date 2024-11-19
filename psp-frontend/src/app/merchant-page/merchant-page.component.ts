import { Component, OnInit } from '@angular/core';
import { MerchantService } from '../service/merchant.service';
import { AuthServiceService } from '../auth/auth-service.service';
import { Merchant } from '../model/merchant.model';
import { User } from '../model/user.model';
import { Subscription } from '../model/subscription.model';

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

  subscriptions: Subscription[] = [];
  notSubscriptions: Subscription[] = []
  featureSubscriptions: Subscription[] = [];

  showAssignModal: boolean = false;

  constructor(private service: MerchantService, private auth: AuthServiceService){}
  
  
  ngOnInit(): void {
    this.auth.user$.subscribe(u => {
        this.user = u;
    });
    this.service.getMerchant(this.user.email).subscribe({
      next: (response: Merchant) => {
        if(response == null) return;
        this.merchant = response;
        this.getSubscribedPayments(this.merchant.businessEmail);
      }
    })
  }

  getSubscribedPayments(email: string): void {
    this.service.getSubscribedPayments(email).subscribe({
      next: (response: Subscription[]) => {
        if(response == null || response == undefined) return;
        this.subscriptions = response;
      }
    })
  }

  addToSubscribe(subscription: Subscription): void {
      this.featureSubscriptions.push(subscription);
  }

  removeToSubscribe(subscription: Subscription): void {
      const index = this.featureSubscriptions.findIndex(sub => sub.name === subscription.name);
      if(index < 0) return;
      this.featureSubscriptions.splice(index, 1)
  }

  subscribe(): void{

  }

  showModal(): void{
    this.service.getNotSubscribedPayments(this.merchant.businessEmail).subscribe({
      next: (response: Subscription[]) => {
        if(response == null || response == undefined) return;
        this.notSubscriptions = response;
        this.showAssignModal = true;
      }
    })
  }

  hideModal(): void{
    this.featureSubscriptions = [];
    this.showAssignModal = false;
  }

  // If index is > -1 subscriptions is present in featureSubscriptions list
  checkIfContains(subscription: Subscription): boolean {
    const index = this.featureSubscriptions.findIndex(sub => sub.name === subscription.name);
    return index > -1;
  }

  completeItem(subscription: Subscription): void {
    if(this.checkIfContains(subscription)){
      this.removeToSubscribe(subscription);
      return;
    }
    this.addToSubscribe(subscription);
  }
}