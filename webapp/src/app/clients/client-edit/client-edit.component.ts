import {Component, Input, OnInit} from '@angular/core';
import {ClientService} from '../shared/client.service';
import {ActivatedRoute, Params, Router} from '@angular/router';

import {Client} from '../shared/client.model';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.css']
})
export class ClientEditComponent implements OnInit {

  @Input() client: Client;

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    public router: Router
  ) { }

  ngOnInit() {
    this.route.params
      .pipe(switchMap((params: Params) => {
        return this.clientService.getClient(+params.id);
      }))
      .subscribe(client => this.client = client);
  }

}
