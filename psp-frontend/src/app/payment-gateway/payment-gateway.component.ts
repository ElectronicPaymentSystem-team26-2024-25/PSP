import { Component, OnInit } from '@angular/core';
import { PaymentMethodInfo, Subscription } from '../model/subscription.model';
import { PaymentService } from '../service/payment.service';
import { ActivatedRoute } from '@angular/router';
import { PaymentExecutionRequest } from '../model/payment-execution-request.model';
import { PaymentExecutionResponse } from '../model/payment-execution-response.model';

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
  executePayment(){
    let paymentRequest: PaymentExecutionRequest = {merchantId: '', merchantPassword: '', amount: 0, errorUrl: '', failedUrl: '', merchantOrderId: 0, merchantTimestamp: new Date, successUrl: '', path: ''}
    //ovo treba da se dobije iz veb sopa
    paymentRequest.merchantId = '123e4567-e89b-12d3-a456-426614174000'
    paymentRequest.amount = 100
    //ovo treba da se dobije iz baze psp-a na osnovu merchant Id
    paymentRequest.merchantPassword = '789e1234-e89b-56d3-a456-426614174111'
    // ovo treba da se izgenerise na bekendu
    paymentRequest.merchantOrderId = 2
    paymentRequest.merchantTimestamp = new Date
    paymentRequest.errorUrl = 'http://localhost:4200/error/'+paymentRequest.merchantOrderId
    paymentRequest.failedUrl = 'http://localhost:4200/fail/'+paymentRequest.merchantOrderId
    paymentRequest.successUrl = 'http://localhost:4200/success/'+paymentRequest.merchantOrderId
    if(this.selectedPaymentMethod.type == 'bank'){
      this.executePaymentInBank(paymentRequest)
    }
  }
  executePaymentInBank(paymentRequest: PaymentExecutionRequest){
    //dobiti port banke na osnovu merchant Id iz baze
    let bankPort: string = '8060'
    paymentRequest.path = "api/cardpayment/cardpaymentform/"+bankPort
    this.paymentService.sendBankPaymentRequest(paymentRequest, bankPort).subscribe({
      next: (response: PaymentExecutionResponse) => {
         window.location.href = response.paymentUrl
      },
      error: (error) => {
        alert('error while reaching bank')
      }
    })
  }
}
