import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Constants } from '../config/constants';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css'],
})
export class AdminMenuComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  goToDashboard() {
    this.router.navigate(['/admin_dashboard']);
  }

  goToMuseums() {
    this.router.navigate(['museums']);
  }

  goToCRUD() {
    const userAsString = localStorage.getItem('user');
    if (userAsString != null) {
      const currentUser = JSON.parse(userAsString);
      window.open(
        Constants.ADMIN_CRUD_URL + '?token=' + currentUser.token,
        '_blank'
      );
    }
  }

  goToLogs() {
    this.router.navigate(['/logs']);
  }
}
