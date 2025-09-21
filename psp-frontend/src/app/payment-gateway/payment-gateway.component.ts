import { Component, OnInit } from '@angular/core';
import { PaymentMethod, PaymentType, Subscriptions } from '../model/subscription.model';
import { PaymentService } from '../service/payment.service';
import { ActivatedRoute } from '@angular/router';
import { PaymentExecutionRequest, PayPalRequest } from '../model/payment-execution-request.model';
import { PaymentApproveLink, PaymentExecutionResponse } from '../model/payment-execution-response.model';
import { MerchantOrder } from '../model/merchant-order.model';
import { MerchantInfo } from '../model/merchant-info.model';
import { frontend_url } from '../env/environment';

@Component({
  selector: 'app-payment-gateway',
  templateUrl: './payment-gateway.component.html',
  styleUrls: ['./payment-gateway.component.css']
})
export class PaymentGatewayComponent implements OnInit{
  
  orderLink: string = ''
  merchantOrder?: MerchantOrder
  merchantInfo?: MerchantInfo
  
  selectedPaymentMethod: PaymentMethod = {
    id: 0,
    name: '',
    type: PaymentType.bank,
    address: '',
    endpoint: ''
  };

  subscription: Subscriptions = {
    merchantEmail: '',
    paymentMethods: []
  };


  constructor(private route: ActivatedRoute, private paymentService: PaymentService) {}
  
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['merchant']
      this.orderLink = params['order']
      this.paymentService.getMerchantsSubscribed(token).subscribe({
        next: (response: Subscriptions) => {
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

  selectPaymentMethod(method: PaymentMethod): void {
      this.selectedPaymentMethod = method;
  }

  get bankMethods(): PaymentMethod[] {
    return this.subscription.paymentMethods.filter(
      (method) => method.type === PaymentType.bank
    );
  }

  get walletMethods(): PaymentMethod[] {
    return this.subscription.paymentMethods.filter(
      (method) => method.type === PaymentType.wallet
    );
  }

  get cryptoMethods(): PaymentMethod[] {
    return this.subscription.paymentMethods.filter(
      (method) => method.type === PaymentType.crypto
    );
  }
  executePayment(){
    if(this.selectedPaymentMethod.type === PaymentType.crypto){
      this.executePaymentWithPayPal()
    }
    
    let paymentRequest: PaymentExecutionRequest = {merchantId: '', merchantPassword: '', amount: 0, errorUrl: '', failedUrl: '', merchantOrderId: 0, merchantTimestamp: new Date, successUrl: '', path: ''}
    paymentRequest.merchantId = this.merchantOrder?.merchantId!
    paymentRequest.amount = this.merchantOrder?.amount!
    paymentRequest.merchantPassword = this.merchantInfo?.merchantPassword!
    paymentRequest.merchantOrderId = this.merchantOrder?.merchantOrderId!
    paymentRequest.merchantTimestamp = this.merchantOrder?.merchantTimestamp!
    paymentRequest.errorUrl = 'https://localhost:4200/error/'+paymentRequest.merchantOrderId
    paymentRequest.failedUrl = 'https://localhost:4200/fail/'+paymentRequest.merchantOrderId
    paymentRequest.successUrl = 'https://localhost:4200/success/'+paymentRequest.merchantOrderId
    if(this.selectedPaymentMethod.type == PaymentType.bank){
      this.executePaymentInBank(paymentRequest)
    }
  }

  executePaymentWithPayPal() : void {
    let merchantOrderId = this.merchantOrder?.merchantId || ""
    const paymentRequest : PayPalRequest = {
      merchantId: merchantOrderId,
      amount: String(this.merchantOrder?.amount) || "",
      merchantOrderId: String(this.merchantOrder?.merchantOrderId) || "",
      merchantTimestamp: this.merchantOrder?.merchantTimestamp!,
      sucessUrl: frontend_url + '/success/' + merchantOrderId,
      errorUrl: frontend_url + '/error/' + merchantOrderId,
      failedUrl: frontend_url + '/fail/' + merchantOrderId,
      brandName: "Telekom Inc."
    }
    this.paymentService.sendPayPalRequest(paymentRequest).subscribe({
      next: (response: PaymentApproveLink) => {
        window.location.href = response.message
      },
      error: () => {
        alert('Error while reaching: ' + this.selectPaymentMethod.name)
      }
    })
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
