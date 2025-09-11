import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from '../service/payment.service';
import { FailReason } from '../model/fail-reason.model';

@Component({
  selector: 'app-failed-page',
  templateUrl: './failed-page.component.html',
  styleUrls: ['./failed-page.component.css']
})
export class FailedPageComponent {
  id: string | null = null;
  reason: string | null = null;
  constructor(private route: ActivatedRoute, private service: PaymentService) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('orderId');
    this.service.getFailReason(this.id!).subscribe({
      next: (response: FailReason) => {
        this.reason = response.failReason
      }
    })
  }
  
  onFinish(){
    window.location.href = 'https://localhost:5173/successpage/'+this.id
  }
}
