import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../config/constants';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  login(user: User): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    const body = JSON.stringify(user);
    return this.http.post(Constants.API_URL + '/login', body, {
      headers: headers,
    });
  }
}
