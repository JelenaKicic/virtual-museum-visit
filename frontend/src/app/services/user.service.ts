import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../config/constants';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(Constants.API_URL + '/users', {
      headers: headers,
    });
  }

  getActionsStatistic(): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(Constants.API_URL + '/logs/statistics', {
      headers: headers,
    });
  }

  getActiveUsersCount(): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(Constants.API_URL + '/users/count/active', {
      headers: headers,
    });
  }

  getRegisteredUsersCount(): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.get(Constants.API_URL + '/users/count/registered', {
      headers: headers,
    });
  }

  activateUser(userId: number): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.put(
      Constants.API_URL + '/users/' + userId + '/status/active',
      {},
      {
        headers: headers,
      }
    );
  }

  resetUsersPassword(userId: number): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.put(
      Constants.API_URL + '/users/' + userId + '/reset-password',
      {},
      {
        headers: headers,
      }
    );
  }

  blockUser(userId: number): Observable<any> {
    const headers = {
      'content-type': 'application/json',
    };
    return this.http.put(
      Constants.API_URL + '/users/' + userId + '/status/block',
      {},
      {
        headers: headers,
      }
    );
  }
}
