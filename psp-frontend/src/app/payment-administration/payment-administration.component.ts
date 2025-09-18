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

  deleteMethod(method: PaymentMethod): void{
      const id = method.id;
      this.service.deleteMethod(method).subscribe({
        next: (response: any) => {
            alert('Deleted payment method with id: ' + id);
            const index = this.methods.findIndex(met => met.id === id);
            if(index < 0) return;
            this.methods.splice(index, 1);
        },
        error: (err: any) => {
          alert(err);
        }
      })
  }
}
