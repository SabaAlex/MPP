import { Component, OnInit } from '@angular/core';
import {ClientService} from '../shared/client.service';
import {Router} from '@angular/router';
import {Client} from '../shared/client.model';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  currentOutlet: number;
  clients: Client[];

  constructor(
    private clientService: ClientService,
    private router: Router
  ) {
    this.currentOutlet = -1;
  }

  ngOnInit() {
    this.clientService.getAllClients()
      .subscribe(clients => {
        console.log('getAllClients --- subscribed: result = ', clients);
        this.clients = clients;
      });
  }

  showOutlet(clientID) {
    this.currentOutlet = clientID;
  }

}
