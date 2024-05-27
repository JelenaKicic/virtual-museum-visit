import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ExternalApisService {
  constructor(private http: HttpClient) {}

  getCountries(): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get('https://restcountries.com/v3.1/region/europe', {
      headers: headers,
    });
  }

  getCountryRegions(countryCode: string): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(
      'http://battuta.medunes.net/api/region/' +
        countryCode +
        '/all/?key=a54f0e266eac78bc77905f0b0bc4a1f6',
      {
        headers: headers,
      }
    );
  }

  getRegionCities(countryCode: string, region: string): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(
      'http://battuta.medunes.net/api/city/' +
        countryCode +
        '/search/?region=' +
        region +
        '&key=a54f0e266eac78bc77905f0b0bc4a1f6',
      {
        headers: headers,
      }
    );
  }

  getNews(): Observable<any> {
    return this.http.get(
      'https://api.rss2json.com/v1/api.json?rss_url=https://www.huffpost.com/section/arts/feed'
    );
  }

  getWeather(lat: number, lon: number): Observable<any> {
    return this.http.get(
      'https://api.openweathermap.org/data/2.5/weather?lat=' +
        lat +
        '&lon=' +
        lon +
        '&appid=fb963d1b409335d6ae16552eab807ade'
    );
  }
}
