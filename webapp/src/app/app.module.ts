import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {ClientService} from './clients/shared/client.service';
import { AppComponent } from './app.component';
import { MoviesComponent } from './movies/movies.component';
import { RentalsComponent } from './rentals/rentals.component';
import { ClientDetailsComponent } from './clients/client-details/client-details.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import { ClientEditComponent } from './clients/client-edit/client-edit.component';
import {RouterModule} from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import {ClientsComponent} from './clients/clients.component';
import {HttpClientModule} from '@angular/common/http';
import { MovieListComponent } from './movies/movie-list/movie-list.component';
import { MovieDetailsComponent } from './movies/movie-details/movie-details.component';
import { MovieEditComponent } from './movies/movie-edit/movie-edit.component';
import {MovieService} from './movies/shared/movie.service';
import { RentalEditComponent } from './rentals/rental-edit/rental-edit.component';
import { RentalListComponent } from './rentals/rental-list/rental-list.component';
import { RentalDetailsComponent } from './rentals/rental-details/rental-details.component';
import {RentalService} from './rentals/shared/rental.service';

@NgModule({
  declarations: [
    AppComponent,
    MoviesComponent,
    RentalsComponent,
    ClientsComponent,
    ClientDetailsComponent,
    ClientListComponent,
    ClientEditComponent,
    MoviesComponent,
    MovieListComponent,
    MovieDetailsComponent,
    MovieEditComponent,
    RentalsComponent,
    RentalEditComponent,
    RentalListComponent,
    RentalDetailsComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [ClientService, MovieService, RentalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
