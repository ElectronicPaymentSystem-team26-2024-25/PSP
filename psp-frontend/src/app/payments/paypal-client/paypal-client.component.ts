import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PayPalClient } from '../model/payments.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PaymentService } from 'src/app/service/payment.service';

@Component({
  selector: 'app-paypal-client',
  templateUrl: './paypal-client.component.html',
  styleUrls: ['./paypal-client.component.css']
})
export class PaypalClientComponent implements OnInit{
  
  @Input({required: true}) merchantId: string = "";
  @Input({required: true}) methodId: number = -1;
  @Output() successfullEvent = new EventEmitter<null>();

  clientForm = new FormGroup({
    email: new FormControl('', [Validators.required]), 
    clientId: new FormControl('', [Validators.required]),
    clientSecret: new FormControl('', [Validators.required])
  });

  constructor(private paymentService: PaymentService){}

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  createClient(): void {
    if(!this.clientForm.valid) {
      alert("One or more fields are invalid. Try again.")
      return;
    }
    const client: PayPalClient = {
      merchantId: this.merchantId,
      email: this.clientForm.value.email || "",
      clientId: this.clientForm.value.clientId || "",
      clientSecret: this.clientForm.value.clientSecret || ""
    }
    this.paymentService.createPayPalClient(client, this.methodId).subscribe({
      next: (response: PayPalClient) => {
          if(response == null || response == undefined){
            alert("Something went wrong.")
            return;
          }
          alert('PayPal credentials successfully updated.')
          this.successfullEvent.emit();
      },
      error: err => {
        alert('Error: ' + err.status + ':' + err.statusText);
      }
    })
  }
}
