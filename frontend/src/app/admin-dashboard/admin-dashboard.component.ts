import { Component, OnInit } from '@angular/core';
import { Staistic } from '../models/statistic.model';
import { User } from '../models/user.model';
import { UserService } from '../services/user.service';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
})
export class AdminDashboardComponent implements OnInit {
  displayedColumns: string[] = [
    'username',
    'name',
    'status',
    'activate',
    'resetPassword',
    'block',
  ];
  public activeUsersCount: number = 0;
  public regiteredUsersCount: number = 0;
  public users: User[] = [];
  public statistics: Staistic[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getActiveUsersCount().subscribe((value) => {
      this.activeUsersCount = value.count;
    });

    this.userService.getRegisteredUsersCount().subscribe((value) => {
      this.regiteredUsersCount = value.count;
    });

    this.userService.getActionsStatistic().subscribe((value) => {
      this.statistics = value;
      this.populateChart();
    });

    this.loadUsers();
  }

  populateChart() {
    Chart.register(...registerables);

    let labels: Array<string> = new Array();
    let values: Array<number> = new Array();

    this.statistics.forEach((item) => {
      labels.push(item.period);
      values.push(item.count);
    });
    const statisticChart = new Chart('statisticChart', {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [
          {
            label:
              'Number per hour of active users in last 24 hours, starting from now',
            data: values,
          },
        ],
      },
    });
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe((value) => {
      this.users = value;
    });
  }

  blockUser(userId: number) {
    this.userService.blockUser(userId).subscribe((value) => {
      this.loadUsers();
    });
  }

  activateUser(userId: number) {
    this.userService.activateUser(userId).subscribe((value) => {
      this.loadUsers();
    });
  }

  resetUsersPassword(userId: number) {
    this.userService.resetUsersPassword(userId).subscribe((value) => {
      this.loadUsers();
    });
  }
}
