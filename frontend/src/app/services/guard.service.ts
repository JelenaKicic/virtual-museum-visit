import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { User } from '../models/user.model';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root',
})
export class GuardService implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    let userAsString = localStorage.getItem('user');
    if (userAsString != null) {
      let user = JSON.parse(userAsString);
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
