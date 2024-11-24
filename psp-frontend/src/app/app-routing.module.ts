import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { PaymentGatewayComponent } from './payment-gateway/payment-gateway.component';
import { MerchantPageComponent } from './merchant-page/merchant-page.component';
import { AuthGuard } from './auth/guards/auth.guard';
import { RoleGuard } from './auth/guards/role.guard';
import { ForbiddenComponent } from './auth/forbidden/forbidden.component';
import { SuccessPageComponent } from './success-page/success-page.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { FailedPageComponent } from './failed-page/failed-page.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'pay.psp', component: PaymentGatewayComponent},
  {path: 'merchant-page', component: MerchantPageComponent, canActivate: [AuthGuard, RoleGuard], data: {expectedRoles: ['merchant']}},
  {path: 'forbidden-page', component: ForbiddenComponent},
  {path: 'success/:orderId', component: SuccessPageComponent},
  {path: 'error/:orderId', component: ErrorPageComponent},
  {path: 'fail/:orderId', component: FailedPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
