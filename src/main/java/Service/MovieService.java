package Service;

import model.domain.Client;
import model.domain.Movie;
import model.domain.validators.ValidatorException;
import repository.IRepository;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService {
    private IRepository<Long, Movie> repository;

    public MovieService(IRepository<Long,Movie> repository)
    {
        this.repository=repository;
    }

    public void addMovie(Movie movie) throws ValidatorException
    {
        repository.save(movie);
    }

    public Set<Movie> getAllMovies()
    {
        Iterable<Movie> movies=repository.findAll();
        return StreamSupport.stream(movies.spliterator(),false).collect(Collectors.toSet());

    }

    public Set<Movie> filterMoviesByTitle(String name)
    {
        Iterable<Movie> movies=repository.findAll();
        Set<Movie> filteredMovies=new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie->!(movie.getTitle().contains(name)) );
        return filteredMovies;
    }



}