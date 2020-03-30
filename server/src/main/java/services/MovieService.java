package services;

import model.domain.Movie;
import model.exceptions.MyException;
import model.validators.Validator;
import repository.IRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService extends BaseService<Long, Movie> {
    public MovieService(IRepository<Long, Movie> repository, Validator<Movie> validator, ExecutorService executor) {
        super(repository, validator, "Movie",executor);
    }

    @Override
    public Set<Movie> filterEntitiesField(String field) {
        Iterable<Movie> movies = repository.findAll();
        Set<Movie> filteredMovies=new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie->!(movie.getTitle().contains(field)) );
        return filteredMovies;
    }

    @Override
    public List<Movie> statEntities(String... fields) {
        if (fields.length != 0)
            throw new MyException("Something went wrong!");

        List<Movie> movieList = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());

        return movieList.stream()
                .collect(Collectors.groupingBy(Movie::getYearOfRelease))
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().size() - o1.getValue().size())
                .findFirst()
                .get()
                .getValue()
                ;
    }
}
