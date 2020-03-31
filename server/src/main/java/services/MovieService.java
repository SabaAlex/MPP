package services;

import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.validators.Validator;
import repository.IRepository;
import repository.Sort;
import repository.SortingRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService extends BaseService<Long, Movie> {
    public MovieService(IRepository<Long, Movie> repository, Validator<Movie> validator, ExecutorService executor) {
        super(repository, validator, "Movie",executor);
    }

    @Override
    public CompletableFuture<Set<Movie>> filterEntitiesField(String field) {
        Iterable<Movie> movies = repository.findAll();
        Set<Movie> filteredMovies=new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie->!(movie.getTitle().contains(field)) );
        return CompletableFuture.supplyAsync(() -> filteredMovies, executorService);
    }

    @Override
    public CompletableFuture<List<Movie>> statEntities(String... fields) {
        if (fields.length != 0)
            throw new MyException("Something went wrong!");

        List<Movie> movieList = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());

        return CompletableFuture.supplyAsync(() -> movieList.stream()
                .collect(Collectors.groupingBy(Movie::getYearOfRelease))
                .entrySet()
                .stream()
                .max((o1, o2) -> o2.getValue().size() - o1.getValue().size())
                .get()
                .getValue(), executorService);
    }

    @Override
    public CompletableFuture<List<Movie>> getAllEntitiesSorted() {
        if(repository instanceof SortingRepository)
        {
            Sort sort = new Sort( "Genre").and(new Sort(Sort.Direction.DESC, "YearOfRelease"));
            sort.setClassName("Movie");
            Iterable<Movie> entities=((SortingRepository<Long, Movie>) repository).findAll(sort);
            List<Movie> entity_set = StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
            return CompletableFuture.supplyAsync(() -> entity_set, executorService);
        }
        throw new MyException("This is not A SUPPORTED SORTING REPOSITORY");
    }
}
