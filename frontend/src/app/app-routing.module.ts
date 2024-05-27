import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminCreateMuseumComponent } from './admin-create-museum/admin-create-museum.component';
import { AdminCreateTourComponent } from './admin-create-tour/admin-create-tour.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminMuseumsComponent } from './admin-museums/admin-museums.component';
import { BuyTourComponent } from './buy-tour/buy-tour.component';
import { LoginComponent } from './login/login.component';
import { LogsComponent } from './logs/logs.component';
import { MuseumDetailsComponent } from './museum-details/museum-details.component';
import { RegistrationComponent } from './registration/registration.component';
import { GuardService } from './services/guard.service';
import { UserActiveToursComponent } from './user-active-tours/user-active-tours.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegistrationComponent,
  },
  {
    path: 'admin_dashboard',
    component: AdminDashboardComponent,
    canActivate: [GuardService],
  },
  {
    path: 'user_dashboard',
    component: UserDashboardComponent,
    canActivate: [GuardService],
  },
  {
    path: 'logs',
    component: LogsComponent,
    canActivate: [GuardService],
  },
  {
    path: 'museums',
    component: AdminMuseumsComponent,
    canActivate: [GuardService],
  },
  {
    path: 'add_museum',
    component: AdminCreateMuseumComponent,
    canActivate: [GuardService],
  },
  {
    path: 'museums/:museum_id',
    component: MuseumDetailsComponent,
    canActivate: [GuardService],
  },
  {
    path: 'my_tours',
    component: UserActiveToursComponent,
    canActivate: [GuardService],
  },
  {
    path: 'buy_tour/:tour_id/museums/:museum_id',
    component: BuyTourComponent,
    canActivate: [GuardService],
  },
  {
    path: 'museums/:museum_id/add_tour',
    component: AdminCreateTourComponent,
    canActivate: [GuardService],
  },
  {
    path: '**',
    redirectTo: '/',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
