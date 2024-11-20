import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from 'src/app/auth/auth-service.service';
import { User } from 'src/app/model/user.model';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  
  user: User | undefined;
  
  constructor(private authService: AuthServiceService) {}
  
  ngOnInit(): void {
      this.authService.user$.subscribe(user => {
        this.user = user;
      })
  }

  onLogout(): void {
    this.authService.logout();
  }

}
