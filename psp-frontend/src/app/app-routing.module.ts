import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { PaymentGatewayComponent } from './payment-gateway/payment-gateway.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'pay.psp', component: PaymentGatewayComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
