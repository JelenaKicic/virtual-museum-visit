import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Museum } from '../models/museum.model';
import { Tour } from '../models/tour.model';
import { ExternalApisService } from '../services/external-apis.service';
import { MuseumService } from '../services/museum.service';
import { TourService } from '../services/tour.service';

@Component({
  selector: 'app-museum-details',
  templateUrl: './museum-details.component.html',
  styleUrls: ['./museum-details.component.css'],
})
export class MuseumDetailsComponent implements OnInit {
  displayedColumns: string[] = [
    'startDate',
    'startTime',
    'length',
    'price',
    'buy_tour',
  ];
  public museum: Museum | undefined;
  public tours: Tour[] = [];
  public mapMarker: any;

  public cities: {
    city: string;
    country: string;
    latitude: number;
    longitude: number;
  }[] = [];

  public weather1:
    | {
        id: string;
        main: string;
        description: number;
        icon: number;
      }
    | undefined;

  public city1:
    | {
        city: string;
        country: string;
        latitude: number;
        longitude: number;
      }
    | undefined;

  public weather2:
    | {
        id: string;
        main: string;
        description: number;
        icon: number;
      }
    | undefined;

  public city2:
    | {
        city: string;
        country: string;
        latitude: number;
        longitude: number;
      }
    | undefined;

  public weather3:
    | {
        id: string;
        main: string;
        description: number;
        icon: number;
      }
    | undefined;

  public city3:
    | {
        city: string;
        country: string;
        latitude: number;
        longitude: number;
      }
    | undefined;

  constructor(
    private museumService: MuseumService,
    private externalApisService: ExternalApisService,
    private tourService: TourService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    let museumId = Number(this.route.snapshot.paramMap.get('museum_id'));
    this.museumService.getMuseumById(museumId).subscribe((value) => {
      this.museum = value;

      this.loadCities();

      this.mapMarker = {
        position: {
          lat: this.museum?.latitude,
          lng: this.museum?.longitude,
        },
        label: {
          color: 'red',
          text: this.museum?.address,
        },
        title: this.museum?.name,
        options: { animation: google.maps.Animation.BOUNCE },
      };
    });

    this.tourService.getToursByMuseumId(museumId).subscribe((value) => {
      this.tours = value;
    });
  }

  buyTour(tourId: number) {
    this.router.navigate(['buy_tour', tourId, 'museums', this.museum?.id], {
      state: { tourId: tourId },
    });
  }

  loadCities() {
    if (this.museum?.country != null && this.museum?.region != null) {
      this.externalApisService
        .getRegionCities(this.museum?.country, this.museum?.region)
        .subscribe((value) => {
          this.cities = value;

          const rndIndex1 = Math.floor(Math.random() * this.cities.length);
          this.city1 = this.cities[rndIndex1];
          this.externalApisService
            .getWeather(
              this.cities[rndIndex1].latitude,
              this.cities[rndIndex1].longitude
            )
            .subscribe((value) => {
              this.weather1 = value.weather[0];
            });

          const rndIndex2 = Math.floor(Math.random() * this.cities.length);
          this.city2 = this.cities[rndIndex2];
          this.externalApisService
            .getWeather(
              this.cities[rndIndex2].latitude,
              this.cities[rndIndex2].longitude
            )
            .subscribe((value) => {
              this.weather2 = value.weather[0];
            });

          const rndIndex3 = Math.floor(Math.random() * this.cities.length);
          this.city3 = this.cities[rndIndex3];
          this.externalApisService
            .getWeather(
              this.cities[rndIndex3].latitude,
              this.cities[rndIndex3].longitude
            )
            .subscribe((value) => {
              this.weather3 = value.weather[0];
            });
        });
    }
  }
}
