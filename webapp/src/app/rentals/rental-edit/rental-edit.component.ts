import {Component, Input, OnInit} from '@angular/core';
import {Movie} from '../../movies/shared/movie.model';
import {MovieService} from '../../movies/shared/movie.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {Rental} from '../shared/rental.model';
import {RentalService} from '../shared/rental.service';

@Component({
  selector: 'app-rental-edit',
  templateUrl: './rental-edit.component.html',
  styleUrls: ['./rental-edit.component.css']
})
export class RentalEditComponent implements OnInit {

  @Input() rental: Rental;

  constructor(
    private rentalService: RentalService,
    private route: ActivatedRoute,
    public router: Router
  ) { }

  ngOnInit() {
    this.route.params
      .pipe(switchMap((params: Params) => {
        return this.rentalService.getRental(+params.id);
      }))
      .subscribe(rental => this.rental = rental);
  }


}
