import { Component, OnInit } from '@angular/core';
import { PaymentMethodInfo, Subscription } from '../model/subscription.model';
import { PaymentService } from '../service/payment.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-payment-gateway',
  templateUrl: './payment-gateway.component.html',
  styleUrls: ['./payment-gateway.component.css']
})
export class PaymentGatewayComponent implements OnInit{
  
  selectedPaymentMethod: PaymentMethodInfo = {
    name: '',
    type: ''
  };

  subscription: Subscription = {
    merchantEmail: '',
    paymentMethods: []
  };


  constructor(private route: ActivatedRoute, private paymentService: PaymentService) {}
  
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['merchant']
      this.paymentService.getMerchantsSubscribed(token).subscribe({
        next: (response: Subscription) => {
          this.subscription = response;
        }
      })
    })
  }

  selectPaymentMethod(method: PaymentMethodInfo): void {
      this.selectedPaymentMethod = method;
  }

  get bankMethods(): PaymentMethodInfo[] {
    return this.subscription.paymentMethods.filter(
      (method) => method.type === "bank"
    );
  }

  get walletMethods(): PaymentMethodInfo[] {
    return this.subscription.paymentMethods.filter(
      (method) => method.type === "wallet"
    );
  }

  get cryptoMethods(): PaymentMethodInfo[] {
    return this.subscription.paymentMethods.filter(
      (method) => method.type === "crypto"
    );
  }

}
