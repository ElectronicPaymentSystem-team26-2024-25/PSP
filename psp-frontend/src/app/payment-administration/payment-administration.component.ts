import { Component, OnInit } from '@angular/core';
import { PaymentMethod, StringToPaymentType } from '../model/subscription.model';
import { AdministrationService } from '../service/administration.service';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';

@Component({
  selector: 'app-payment-administration',
  templateUrl: './payment-administration.component.html',
  styleUrls: ['./payment-administration.component.css']
})
export class PaymentAdministrationComponent implements OnInit{
  
  methods: PaymentMethod[] = [];

  showCreate: boolean = false;
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
  
  
  noSpacesValidator(control: AbstractControl): ValidationErrors | null {
    if (control.value && control.value.includes(' ')) {
      return { noSpaces: true };
    }
    return null;
  }

  typeValidator(control: AbstractControl): ValidationErrors | null {
    const allowedTypes = ['Crypto', 'Wallet', 'Bank'];
    if (control.value && !allowedTypes.includes(control.value)) {
      return { invalidType: true };
    }
    return null;
  }

  
  createForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      type: new FormControl('', [Validators.required, this.typeValidator]),
      address: new FormControl('', [Validators.required, this.noSpacesValidator]),
      endpoint: new FormControl('', [Validators.required, this.noSpacesValidator])
    });


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

  showCreateModal(): void{
    this.showCreate = true;
  }

  hideCreateModal(): void{
    this.showCreate = false;
  }

  create(): void{
    if(!this.createForm.valid){
      alert('Check entered values. Somethings is not right.');
      return;
    }
    else{
      const desiredType = StringToPaymentType(this.createForm.value.type || "");
      const paymentMethod: PaymentMethod = {
        id: 0,
        name: this.createForm.value.name || "",
        type: desiredType,
        address: this.createForm.value.address || "",
        endpoint: this.createForm.value.endpoint || ""
      }
      this.service.createMethod(paymentMethod).subscribe({
        next: (response: PaymentMethod) => {
          this.methods.push(response);
          this.hideCreateModal();
        },
        error: (err: any) => {
          alert('Something went wrong. Try again later.');
          this.hideCreateModal();
        }
      }) 
    }
  }
}
