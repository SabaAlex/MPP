import { Component, OnInit } from '@angular/core';
import {MovieService} from '../shared/movie.service';
import {Router} from '@angular/router';
import {Movie} from '../shared/movie.model';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})

export class MovieListComponent implements OnInit {
  currentOutlet: number;
  movies: Movie[];

  constructor(
    private movieService: MovieService,
    private router: Router
  ) {
    this.currentOutlet = -1;
  }

  ngOnInit() {
    this.movieService.getAllMovies()
      .subscribe(movies => {
        console.log('getAllMovies --- subscribed: result = ', movies);
        this.movies = movies;
      });
  }

  showOutlet(clientID) {
    this.currentOutlet = clientID;
  }

}
