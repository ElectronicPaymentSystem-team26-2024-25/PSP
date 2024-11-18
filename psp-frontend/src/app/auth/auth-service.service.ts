import { Injectable } from '@angular/core';
import { User } from '../model/user.model';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { TokenStorage } from './jwt/jwt.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthenticationResponse, LoginCredentials } from './model/auth.model';
import { environment } from '../env/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  user$ = new BehaviorSubject<User>({email: "", id: 0, type: ""});
  
  constructor(private http: HttpClient, private router: Router, private tokenStorage: TokenStorage) {}

  logout(): void {
    this.router.navigate(['/login']).then(_ => {
      this.tokenStorage.clear();
        this.user$.next({email: "", id: 0, type: "" });
      }
    );
  }

  login(login: LoginCredentials): Observable<AuthenticationResponse> {
    return this.http
      .post<AuthenticationResponse>(environment.apiHost + 'auth/login', login)
      .pipe(
        tap((authenticationResponse) => {
          this.tokenStorage.saveAccessToken(authenticationResponse.jwtToken);
          this.setUser(); 
        })
      );
  }

  private setUser(): void {
    const jwtHelperService = new JwtHelperService();
    const accessToken = this.tokenStorage.getAccessToken() || "";
    const user: User = {
      id: +jwtHelperService.decodeToken(accessToken).sub,
      email: jwtHelperService.decodeToken(accessToken).email,
      type: jwtHelperService.decodeToken(accessToken).role
    };
    this.user$.next(user);
  }
}
