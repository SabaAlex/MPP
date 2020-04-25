package services;

import model.domain.Movie;
import model.exceptions.MyException;
import model.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import repository.IRepository;
import repository.Sort;
import repository.SortingRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService extends BaseService<Long, Movie> implements IMovieService {
    @Autowired
    private IMovieService IMovieService;

    public MovieService(IRepository<Long, Movie> repository, Validator<Movie> validator) {
        super(repository, validator, "Movie");
    }

    @Override
    public synchronized Set<Movie> filterEntitiesField(String field) {
        Iterable<Movie> movies = repository.findAll();
        Set<Movie> filteredMovies=new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie->!(movie.getTitle().contains(field)) );
        return filteredMovies;
    }

    @Override
    public synchronized List<Movie> statEntities(String... fields) {
            if (fields.length != 0)
                throw new MyException("Something went wrong!");

            List<Movie> movieList = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());

            return movieList.stream()
                .collect(Collectors.groupingBy(Movie::getYearOfRelease))
                .entrySet()
                .stream()
                .max((o1, o2) -> o2.getValue().size() - o1.getValue().size())
                .get()
                .getValue();
    }

    @Override
    public synchronized List<Movie> getAllEntitiesSorted() {
            if(repository instanceof SortingRepository)
            {
                Sort sort = new Sort( "Genre").and(new Sort(Sort.Direction.DESC, "YearOfRelease"));
                sort.setClassName("Movie");
                Iterable<Movie> entities=((SortingRepository<Long, Movie>) repository).findAll(sort);
                return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
            }
            throw new MyException("This is not A SUPPORTED SORTING REPOSITORY");
    }
}
