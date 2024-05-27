import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const userAsString = localStorage.getItem('user');
    if (userAsString != null) {
      const currentUser = JSON.parse(userAsString);
      if (currentUser && currentUser.token) {
        request = request.clone({
          setHeaders: {
            Authorization: `Bearer ${currentUser.token}`,
          },
        });
      }
    } else {
      this.router.navigate(['login']);
    }

    return next.handle(request).pipe(
      tap(
        () => {},
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status !== 401 && err.status !== 403) {
              return;
            }
            localStorage.removeItem('user');
            this.router.navigate(['login']);
          }
        }
      )
    );
  }
}
