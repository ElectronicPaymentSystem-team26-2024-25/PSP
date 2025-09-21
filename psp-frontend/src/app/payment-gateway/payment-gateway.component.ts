import { Component, OnInit } from '@angular/core';
import { PaymentMethodInfo, Subscriptions } from '../model/subscription.model';
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
  const paymentRequest: PaymentExecutionRequest = {
    merchantId: this.merchantOrder?.merchantId!,
    merchantPassword: this.merchantInfo?.merchantPassword!,
    amount: this.merchantOrder?.amount!,
    merchantOrderId: this.merchantOrder?.merchantOrderId!,
    merchantTimestamp: this.merchantOrder?.merchantTimestamp!,
    successUrl: `https://localhost:4200/success/${this.merchantOrder?.merchantOrderId!}`,
    failedUrl:  `https://localhost:4200/fail/${this.merchantOrder?.merchantOrderId!}`,
    errorUrl:   `https://localhost:4200/error/${this.merchantOrder?.merchantOrderId!}`,
    path: '' 
  };

  if (this.selectedPaymentMethod.type === 'bank') {
    const bankPort: string = this.merchantInfo?.port!;
    paymentRequest.path = `api/cardpayment/cardpaymentform/${bankPort}`;
    this.redirectViaPsp(paymentRequest);
    return;
  }

  if (this.selectedPaymentMethod.type === 'crypto') {
    // CRYPTO: minimalna izmena – samo postavi path na crypto init
    paymentRequest.path = `api/crypto/payment`;
    this.redirectViaPsp(paymentRequest);
    return;
  }

}

/** Jedinstveno slanje ka PSP-u i redirect na paymentUrl (bank i crypto dele isti tok) */
private redirectViaPsp(paymentRequest: PaymentExecutionRequest) {
  this.paymentService.sendBankPaymentRequest(paymentRequest, this.merchantInfo?.port || '')
    .subscribe({
      next: (response: PaymentExecutionResponse) => {
        window.location.href = response.paymentUrl;
      },
      error: () => {
        alert('Error while reaching payment processor');
      }
    });
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
