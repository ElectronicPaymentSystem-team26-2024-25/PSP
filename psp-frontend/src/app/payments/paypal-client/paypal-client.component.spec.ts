import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaypalClientComponent } from './paypal-client.component';

describe('PaypalClientComponent', () => {
  let component: PaypalClientComponent;
  let fixture: ComponentFixture<PaypalClientComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaypalClientComponent]
    });
    fixture = TestBed.createComponent(PaypalClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
