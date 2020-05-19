import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {Rental} from './rental.model';

import {Observable, from} from 'rxjs';

import {map, flatMap, mergeMap} from 'rxjs/operators';


@Injectable()
export class RentalService {
  private rentalsUrl = 'http://localhost:8080/api/rentals';

  constructor(private httpClient: HttpClient) {
  }

  getRental(id: number): Observable<Rental> {
    console.log(`getRental ---- method entered: id = ${id}`);
    return this.httpClient.get<Rental>(this.rentalsUrl + '/' + id).pipe(
      map(result => {
        console.log('getRental --- method finished: result = ', result);
        return result;
      }));
  }

  getAllRentals(): Observable<Rental[]> {
    console.log('getAllRentals --- method entered');
    return this.httpClient.get<Array<Rental>>(this.rentalsUrl).pipe(
      map(result => {
        console.log('getAllRentals --- method finished: result = ', result);
        return result.dtoList;
      }));
  }


}
