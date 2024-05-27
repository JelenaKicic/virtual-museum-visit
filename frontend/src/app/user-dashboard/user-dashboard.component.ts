import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Museum } from '../models/museum.model';
import { ExternalApisService } from '../services/external-apis.service';
import { MuseumService } from '../services/museum.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css'],
})
export class UserDashboardComponent implements OnInit {
  displayedColumns: string[] = ['name', 'type', 'city', 'museum_details'];
  public museums: Museum[] = [];
  public news: any[] = [];
  public searchForm: FormGroup = new FormGroup({});

  constructor(
    private museumService: MuseumService,
    private externalApisService: ExternalApisService,
    private router: Router,
    public formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      nameCity: new FormControl(''),
    });

    this.loadMuseums();

    this.externalApisService.getNews().subscribe((value) => {
      console.log(value.items);
      this.news = value.items;
    });
  }

  loadMuseums() {
    let nameOrCityStart = this.searchForm.get('nameCity')?.value;
    this.museumService
      .getMuseumsByNameAndCity(nameOrCityStart)
      .subscribe((value) => {
        this.museums = value;
      });
  }

  goToMuseumDetails(museumId: number) {
    this.router.navigate(['museums', museumId], {
      state: { museumId: museumId },
    });
  }
}
