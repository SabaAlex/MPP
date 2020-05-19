import { Component, OnInit } from '@angular/core';
import {RentalService} from '../shared/rental.service';
import {Router} from '@angular/router';
import {Rental} from '../shared/rental.model';

@Component({
  selector: 'app-rental-list',
  templateUrl: './rental-list.component.html',
  styleUrls: ['./rental-list.component.css']
})
export class RentalListComponent implements OnInit {

  currentOutlet: number;
  rentals: Rental[];

  constructor(
    private rentalService: RentalService,
    private router: Router
  ) {
    this.currentOutlet = -1;
  }

  ngOnInit() {
    this.rentalService.getAllRentals()
      .subscribe(rentals => {
        console.log('getAllRentals --- subscribed: result = ', rentals);
        this.rentals = rentals;
      });
  }

  showOutlet(clientID) {
    this.currentOutlet = clientID;
  }

}
