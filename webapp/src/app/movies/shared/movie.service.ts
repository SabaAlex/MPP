import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {Movie} from './movie.model';

import {Observable, from} from 'rxjs';

import {map, flatMap, mergeMap} from 'rxjs/operators';


@Injectable()
export class MovieService {
  private moviesUrl = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) {
  }

  getMovie(id: number): Observable<Movie> {
    console.log(`getMovie ---- method entered: id = ${id}`);
    return this.httpClient.get<Movie>(this.moviesUrl + '/' + id).pipe(
      map(result => {
        console.log('getMovie --- method finished: result = ', result);
        return result;
      }));
  }

  getAllMovies(): Observable<Movie[]> {
    console.log('getAllMovies --- method entered');
    return this.httpClient.get<Array<Movie>>(this.moviesUrl).pipe(
      map(result => {
        console.log('getAllMovies --- method finished: result = ', result);
        return result.dtoList;
      }));
  }


}
