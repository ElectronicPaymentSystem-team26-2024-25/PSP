import { Component, OnInit } from '@angular/core';
import { PaymentMethodInfo, Subscription } from '../model/subscription.model';
import { PaymentService } from '../service/payment.service';
import { ActivatedRoute } from '@angular/router';
import { PaymentExecutionRequest } from '../model/payment-execution-request.model';
import { PaymentExecutionResponse } from '../model/payment-execution-response.model';
import { MerchantOrder } from '../model/merchant-order.model';
import { MerchantInfo } from '../model/merchant-info.model';

@Component({
  selector: 'app-payment-gateway',
  templateUrl: './payment-gateway.component.html',
  styleUrls: ['./payment-gateway.component.css']
})
export class PaymentGatewayComponent implements OnInit{
  
  orderLink: string = ''
  merchantOrder?: MerchantOrder
  merchantInfo?: MerchantInfo
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
      this.orderLink = params['order']
      this.paymentService.getMerchantsSubscribed(token).subscribe({
        next: (response: Subscription) => {
          this.subscription = response;
        }
      })
      this.paymentService.getOrder(this.orderLink).subscribe({
        next: (response: MerchantOrder) => {
          this.merchantOrder = response;
          console.log(this.merchantOrder)
          this.paymentService.getMerchantInfo(response.merchantId).subscribe({
            next: (resp: MerchantInfo) => {
              this.merchantInfo = resp;
              console.log(resp)
            }
          })
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
    paymentRequest.merchantId = this.merchantOrder?.merchantId!
    paymentRequest.amount = this.merchantOrder?.amount!
    paymentRequest.merchantPassword = this.merchantInfo?.merchantPassword!
    paymentRequest.merchantOrderId = this.merchantOrder?.merchantOrderId!
    paymentRequest.merchantTimestamp = this.merchantOrder?.merchantTimestamp!
    paymentRequest.errorUrl = 'http://localhost:4200/error/'+paymentRequest.merchantOrderId
    paymentRequest.failedUrl = 'http://localhost:4200/fail/'+paymentRequest.merchantOrderId
    paymentRequest.successUrl = 'http://localhost:4200/success/'+paymentRequest.merchantOrderId
    if(this.selectedPaymentMethod.type == 'bank'){
      this.executePaymentInBank(paymentRequest)
    }
  }
  executePaymentInBank(paymentRequest: PaymentExecutionRequest){
    //dobiti port banke na osnovu merchant Id iz baze
    let bankPort: string = this.merchantInfo?.port!
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
