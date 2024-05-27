import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../config/constants';

@Injectable({
  providedIn: 'root',
})
export class LogsService {
  constructor(private http: HttpClient) {}

  getLogs(): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(Constants.API_URL + '/logs', {
      headers: headers,
    });
  }

  getLogsAsPdf(): Observable<any> {
    const httpOptions = {
      responseType: 'blob' as 'json',
    };
    return this.http.get(Constants.API_URL + '/logs/pdf', httpOptions);
  }
}
