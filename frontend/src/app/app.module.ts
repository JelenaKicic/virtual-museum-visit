import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { RegistrationComponent } from './registration/registration.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AuthInterceptor } from './services/auth.interceptor';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { NgChartsModule } from 'ng2-charts';
import { LogsComponent } from './logs/logs.component';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import { MatMenuModule } from '@angular/material/menu';
import { AdminMuseumsComponent } from './admin-museums/admin-museums.component';
import { AdminCreateTourComponent } from './admin-create-tour/admin-create-tour.component';
import { AdminCreateMuseumComponent } from './admin-create-museum/admin-create-museum.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSelectModule } from '@angular/material/select';
import { DatePipe } from '@angular/common';
import { UserMenuComponent } from './user-menu/user-menu.component';
import { MuseumDetailsComponent } from './museum-details/museum-details.component';
import { BuyTourComponent } from './buy-tour/buy-tour.component';
import { UserActiveToursComponent } from './user-active-tours/user-active-tours.component';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    AdminDashboardComponent,
    UserDashboardComponent,
    LogsComponent,
    AdminMenuComponent,
    AdminMuseumsComponent,
    AdminCreateTourComponent,
    AdminCreateMuseumComponent,
    UserMenuComponent,
    MuseumDetailsComponent,
    BuyTourComponent,
    UserActiveToursComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    NgChartsModule,
    MatMenuModule,
    GoogleMapsModule,
    MatCheckboxModule,
    MatSelectModule,
    MatCardModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    DatePipe,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
