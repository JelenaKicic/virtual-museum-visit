import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../config/constants';
import { Museum } from '../models/museum.model';

@Injectable({
  providedIn: 'root',
})
export class MuseumService {
  constructor(private http: HttpClient) {}

  getMuseumsByNameAndCity(nameOrCityStart: string): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(
      Constants.API_URL + '/museums?nameStart=' + nameOrCityStart,
      {
        headers: headers,
      }
    );
  }

  getMuseumById(museumId: number): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(Constants.API_URL + '/museums/' + museumId, {
      headers: headers,
    });
  }

  addMuseum(museum: Museum): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    const body = JSON.stringify(museum);
    return this.http.post(Constants.API_URL + '/museums', body, {
      headers: headers,
    });
  }
}
