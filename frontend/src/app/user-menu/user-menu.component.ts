import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css'],
})
export class UserMenuComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  goToDashboard() {
    this.router.navigate(['/user_dashboard']);
  }

  goToActiveTours() {
    this.router.navigate(['/my_tours']);
  }
}
