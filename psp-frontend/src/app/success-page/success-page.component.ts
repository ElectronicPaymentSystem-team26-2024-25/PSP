import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from '../service/payment.service';
import { PayPalResponse } from '../model/payment-execution-response.model';

@Component({
  selector: 'app-success-page',
  templateUrl: './success-page.component.html',
  styleUrls: ['./success-page.component.css']
})
export class SuccessPageComponent {
  id: string | null = null;
  reason: string | null = null;
  constructor(private route: ActivatedRoute, private paymentService: PaymentService) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('orderId');
    const token = this.route.snapshot.paramMap.get('token');
    if(token != null){
      this.paymentService.capturePayPalPayment(this.id || '').subscribe({
            next: (response: PayPalResponse) => {
              console.log(response)
            }
          })
    }
  }
  onFinish(){
    window.location.href = 'https://localhost:5173/successpage/'+this.id
  }
}
