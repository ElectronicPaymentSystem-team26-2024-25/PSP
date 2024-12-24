import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PaymentGatewayComponent } from './payment-gateway/payment-gateway.component';
import { MerchantPageComponent } from './merchant-page/merchant-page.component';
import { AuthModule } from './auth/auth.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { JwtInterceptor } from './auth/jwt/jwt.interceptor';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { SuccessPageComponent } from './success-page/success-page.component';
import { FailedPageComponent } from './failed-page/failed-page.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { PaypalClientComponent } from './payments/paypal-client/paypal-client.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    PaymentGatewayComponent,
    MerchantPageComponent,
    NavbarComponent,
    SuccessPageComponent,
    FailedPageComponent,
    ErrorPageComponent,
    PaypalClientComponent
  ],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AuthModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
