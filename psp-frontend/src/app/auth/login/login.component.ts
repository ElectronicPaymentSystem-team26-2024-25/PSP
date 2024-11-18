import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthServiceService } from '../auth-service.service';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user.model';
import {LoginCredentials} from '../model/auth.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  user: User = {
    id: 0,
    email: "",
    type: ""
  }

  constructor(private authService: AuthServiceService, private router: Router) {}

  ngOnInit(): void {
    this.authService.user$.subscribe(user => {
      this.user = user;
      console.log(user.type);
    }); 
  }


  login(): void {
    if (this.loginForm.valid) {
      const loginCredentials: LoginCredentials = {
          email: this.loginForm.value.email || "",
          password: this.loginForm.value.password || ""
      }
      this.authService.login(loginCredentials).subscribe({
        next: () => {
          console.log(this.user);
          if (this.user.type === "merchant") {
            this.router.navigate(['/merchant-page']);
          }
          else{
            //For now its nothing
          }
        },
      });
    }      
  }
}