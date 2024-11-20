import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ForbiddenComponent } from './forbidden/forbidden.component';



@NgModule({
  declarations: [
    LoginComponent,
    ForbiddenComponent
  ],
  imports: [
    ReactiveFormsModule,
    CommonModule
  ]
})
export class AuthModule { }
