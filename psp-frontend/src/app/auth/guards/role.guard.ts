import { Injectable } from '@angular/core';
import * as router from '@angular/router';
import { Observable } from 'rxjs';
import { AuthServiceService } from '../auth-service.service';
import { User } from 'src/app/model/user.model';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements router.CanActivate {
  constructor(
    private router: router.Router,
    private authService: AuthServiceService
  ) {}

  canActivate(route?: router.ActivatedRouteSnapshot):
    | Observable<boolean | router.UrlTree>
    | Promise<boolean | router.UrlTree>
    | boolean
    | router.UrlTree {
        
    const expectedRoles: string[] = route ? route.data['expectedRoles'] : [];    
    const user: User = this.authService.user$.getValue();
    if (expectedRoles.findIndex(r => r === user.type) === -1){
        this.router.navigate(['forbidden-page']);
        return false;
    }
    return true;
  }
}