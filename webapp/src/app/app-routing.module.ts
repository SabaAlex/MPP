import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {RentalsComponent} from './rentals/rentals.component';
import {ClientsComponent} from './clients/clients.component';
import {MoviesComponent} from './movies/movies.component';
import {ClientEditComponent} from './clients/client-edit/client-edit.component';
import {MovieEditComponent} from './movies/movie-edit/movie-edit.component';
import {RentalEditComponent} from './rentals/rental-edit/rental-edit.component';


const routes: Routes = [
  {
    path: 'rentals',
    component: RentalsComponent,
    children: [
      {path: 'edit/:id', component: RentalEditComponent},
    ]
  },
  {
    path: 'clients',
    component: ClientsComponent,
    children: [
      {path: 'edit/:id', component: ClientEditComponent},
    ]
  },
  {path: 'movies',
    component: MoviesComponent,
    children: [
      {path: 'edit/:id', component: MovieEditComponent},
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
