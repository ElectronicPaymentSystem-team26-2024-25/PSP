import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-success-page',
  templateUrl: './success-page.component.html',
  styleUrls: ['./success-page.component.css']
})
export class SuccessPageComponent {
  id: string | null = null;
  reason: string | null = null;
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('orderId');
    
  }
  onFinish(){
    window.location.href = 'https://localhost:5173/successpage/'+this.id
  }
}
