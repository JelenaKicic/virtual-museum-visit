import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../config/constants';
import { Tour } from '../models/tour.model';

@Injectable({
  providedIn: 'root',
})
export class TourService {
  constructor(private http: HttpClient) {}

  getToursByMuseumId(museumId: number): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(Constants.API_URL + '/tours/museums/' + museumId, {
      headers: headers,
    });
  }

  getActiveTours(): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(Constants.API_URL + '/tours_active', {
      headers: headers,
    });
  }

  addTour(tour: Tour, museumId: number): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    const body = JSON.stringify(tour);
    return this.http.post(
      Constants.API_URL + '/tours/museums/' + museumId,
      body,
      {
        headers: headers,
      }
    );
  }

  uploadFiles(tourId: number, files: FormData): Observable<any> {
    return this.http.post(
      Constants.API_URL + '/tours/' + tourId + '/upload-files',
      files
    );
  }

  buyTour(tourId: number, cardDetails: any): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    const body = JSON.stringify(cardDetails);
    return this.http.post(
      Constants.API_URL + '/tours/' + tourId + '/buy',
      body,
      {
        headers: headers,
      }
    );
  }
}
