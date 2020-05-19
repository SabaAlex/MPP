import {Component, Input, OnInit} from '@angular/core';
import {Movie} from '../shared/movie.model';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {MovieService} from '../shared/movie.service';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-movie-edit',
  templateUrl: './movie-edit.component.html',
  styleUrls: ['./movie-edit.component.css']
})
export class MovieEditComponent implements OnInit {

  @Input() movie: Movie;

  constructor(
    private movieService: MovieService,
    private route: ActivatedRoute,
    public router: Router
  ) { }

  ngOnInit() {
    this.route.params
      .pipe(switchMap((params: Params) => {
        return this.movieService.getMovie(+params.id);
      }))
      .subscribe(movie => this.movie = movie);
  }

}
