import { Component, OnInit } from '@angular/core';
import { MerchantService } from '../service/merchant.service';
import { AuthServiceService } from '../auth/auth-service.service';
import { Merchant } from '../model/merchant.model';
import { User } from '../model/user.model';
import { PaymentMethodInfo, Subscription, Subscriptions } from '../model/subscription.model';
import { PaymentService } from '../service/payment.service';

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

  selectedSubscription: Subscription = {
    paymentMethodName: '',
    merchantId: -1,
    paymentMethodId: -1,
    active: true
  }

  subscriptions: Subscription[] = [];
  notSubscriptions: PaymentMethodInfo[] = []
  featureSubscriptions: PaymentMethodInfo[] = [];

  showAssign: boolean = false;
  showEdit: boolean = false;

  constructor(private service: MerchantService, private auth: AuthServiceService, private paymentService: PaymentService){}
  
  
  ngOnInit(): void {
    this.auth.user$.subscribe(u => {
        this.user = u;
    });
    this.service.getMerchant(this.user.email).subscribe({
      next: (response: Merchant) => {
        if(response == null) return;
        this.merchant = response;
        this.getSubscriptions(this.merchant.businessEmail);
      }
    })
  }

  addToSubscribe(subscription: PaymentMethodInfo): void {
      this.featureSubscriptions.push(subscription);
  }

  removeToSubscribe(subscription: PaymentMethodInfo): void {
      const index = this.featureSubscriptions.findIndex(sub => sub.name === subscription.name);
      if(index < 0) return;
      this.featureSubscriptions.splice(index, 1)
  }

  subscribe(): void {
      if(this.featureSubscriptions.length < 1) return;

      const subscription: Subscriptions = {
        merchantEmail: this.merchant.businessEmail,
        paymentMethods: this.featureSubscriptions
      }
      this.paymentService.subscribe(subscription).subscribe({
          next: (response: any) => {
            this.getSubscriptions(this.merchant.businessEmail)
          }
      });
  }

  unsubscribe(sub: Subscription): void {
    this.paymentService.unsubscribe(sub).subscribe({
      next: () => {
        this.getSubscriptions(this.merchant.businessEmail);
      }
    })
  }

  changeStatus(sub: Subscription): void {
      this.service.changeStatus(sub).subscribe({
        next: (response: Subscription) => {
            if(response == null || response == undefined) return;
            const index = this.subscriptions.findIndex(subscription => 
              subscription.merchantId === sub.merchantId && subscription.paymentMethodId == sub.paymentMethodId)
            if(index === -1) return;
            this.subscriptions[index] = response;  
        },
        error: (err: any) => {
            console.log(err);
        }
      })
  }

  showAssignModal(): void{
    this.service.getNotSubscribedPayments(this.merchant.businessEmail).subscribe({
      next: (response: PaymentMethodInfo[]) => {
        if(response == null || response == undefined) return;
        this.notSubscriptions = response;
        this.showAssign = true;
      }
    })
  }

  hideAssignModal(): void{
    this.featureSubscriptions = [];
    this.showAssign = false;
  }

  showEditModal(sub: Subscription): void {
    //TODO: Add logic  
    this.selectedSubscription = sub;
    this.showEdit = true;
  }

  hideEditModal(): void{
    //TODO: Add logic
    this.showEdit = false;
  }



  // If index is > -1 subscriptions is present in featureSubscriptions list
  checkIfContains(subscription: PaymentMethodInfo): boolean {
    const index = this.featureSubscriptions.findIndex(sub => sub.name === subscription.name);
    return index > -1;
  }

  completeItem(subscription: PaymentMethodInfo): void {
    if(this.checkIfContains(subscription)){
      this.removeToSubscribe(subscription);
      return;
    }
    this.addToSubscribe(subscription);
  }

  private getSubscriptions(email: string) {
    this.service.getSubscribedPayments(email).subscribe({
      next: (response: Subscription[]) => {
        if (response == null || response == undefined) return;
        this.subscriptions = response;
      }
    });
  }
}