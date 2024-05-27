import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Museum } from '../models/museum.model';
import { MuseumService } from '../services/museum.service';

@Component({
  selector: 'app-admin-museums',
  templateUrl: './admin-museums.component.html',
  styleUrls: ['./admin-museums.component.css'],
})
export class AdminMuseumsComponent implements OnInit {
  displayedColumns: string[] = [
    'name',
    'city',
    'type',
    'phone_number',
    'add_tour',
  ];
  public museums: Museum[] = [];

  constructor(private museumService: MuseumService, private router: Router) {}

  ngOnInit(): void {
    this.museumService.getMuseumsByNameAndCity('').subscribe((value) => {
      this.museums = value;
    });
  }

  addTour(museumId: number) {
    this.router.navigate(['museums', museumId, 'add_tour'], {
      state: { museumId: museumId },
    });
  }

  addMuseum() {
    this.router.navigate(['add_museum']);
  }
}
