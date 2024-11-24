import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-failed-page',
  templateUrl: './failed-page.component.html',
  styleUrls: ['./failed-page.component.css']
})
export class FailedPageComponent {
  id: string | null = null;
  reason: string | null = null;
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('orderId');
  }
}
