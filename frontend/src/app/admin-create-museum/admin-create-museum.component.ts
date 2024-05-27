import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Country } from '../models/country.model';
import { Museum } from '../models/museum.model';
import { ExternalApisService } from '../services/external-apis.service';
import { MuseumService } from '../services/museum.service';

@Component({
  selector: 'app-admin-create-museum',
  templateUrl: './admin-create-museum.component.html',
  styleUrls: ['./admin-create-museum.component.css'],
})
export class AdminCreateMuseumComponent implements OnInit {
  public countries: Country[] = [];
  public regions: { region: string }[] = [];
  public cities: {
    city: string;
    country: string;
    latitude: number;
    longitude: number;
  }[] = [];

  public createMuseumForm: FormGroup = new FormGroup({});

  constructor(
    public formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private externalApisService: ExternalApisService,
    private museumService: MuseumService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createMuseumForm = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      type: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      phoneNumber: new FormControl('', [Validators.required]),
      longitude: new FormControl('', [Validators.required]),
      latitude: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      region: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
    });

    this.externalApisService.getCountries().subscribe((value) => {
      this.countries = value;
    });
  }

  loadRegions(countryCode: string) {
    this.externalApisService
      .getCountryRegions(countryCode)
      .subscribe((value) => {
        this.regions = value;
        this.cities = [];
        this.setCoordinates(0, 0);
      });
  }

  loadCities(region: string) {
    const countryCode = this.createMuseumForm.get('country')?.value.cca2;
    this.externalApisService
      .getRegionCities(countryCode, region)
      .subscribe((value) => {
        this.cities = value;
        this.setCoordinates(0, 0);
      });
  }
  setCoordinates(latitude: number, longitude: number) {
    this.createMuseumForm.patchValue({
      longitude: longitude,
      latitude: latitude,
    });
  }

  onSubmit({ value, valid }: { value: Museum; valid: boolean }) {
    if (valid) {
      value.country = this.createMuseumForm.get('country')?.value.cca2;
      value.region = this.createMuseumForm.get('region')?.value.region;
      value.city = this.createMuseumForm.get('city')?.value.city;
      this.museumService.addMuseum(value).subscribe(
        (museum) => {
          this.router.navigate(['/museums']);
        },
        (err) => {
          this.snackBar.open(err.error, undefined, {
            duration: 5000,
          });
        }
      );
    }
  }
}
