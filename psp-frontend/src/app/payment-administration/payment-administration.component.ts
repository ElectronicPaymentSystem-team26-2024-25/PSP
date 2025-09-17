import { Component, OnInit } from '@angular/core';
import { PaymentMethod, PaymentType } from '../model/subscription.model';
import { AdministrationService } from '../service/administration.service';
import { PaymentTypeToString } from '../model/subscription.model';

@Component({
  selector: 'app-payment-administration',
  templateUrl: './payment-administration.component.html',
  styleUrls: ['./payment-administration.component.css']
})
export class PaymentAdministrationComponent implements OnInit{
  
  methods: PaymentMethod[] = [];

  showAssign: boolean = false;
  showEdit: boolean = false;

  constructor(private service: AdministrationService) {}

  ngOnInit(): void {
    this.service.getMethods().subscribe({
      next: (response: PaymentMethod[]) => {
        if(response == null) return;
        this.methods = response;
      }
    })
  }

  /**
   * paymentTypeToString
method: PaymentMethodType   */
  public paymentTypeToString(method: PaymentType) {
    return PaymentTypeToString(method);
  }

}
