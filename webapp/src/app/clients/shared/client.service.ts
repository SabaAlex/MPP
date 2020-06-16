import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {Client} from './client.model';

import {Observable, from} from 'rxjs';

import {map, flatMap, mergeMap} from 'rxjs/operators';


@Injectable()
export class ClientService {
  private clientsUrl = 'http://localhost:8080/clients';

  constructor(private httpClient: HttpClient) {
  }

  getClient(id: number): Observable<Client> {
    console.log(`getClient ---- method entered: id = ${id}`);
    return this.httpClient.get<Client>(this.clientsUrl + '/' + id).pipe(
      map(result => {
        console.log('getClient --- method finished: result = ', result);
        return result;
      }));
  }

  getAllClients(): Observable<Client[]> {
    console.log('getAllClients --- method entered');
    return this.httpClient.get<Array<Client>>(this.clientsUrl).pipe(
      map(result => {
        console.log('getAllClients --- method finished: result = ', result);
        return result;
      }));
  }


}
