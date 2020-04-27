package Service;

import model.domain.Movie;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.postgreSQL.jpa.MovieJPARepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService implements IMovieService {
    @Autowired
    protected MovieJPARepository repository;

    public MovieService(MovieJPARepository repository) {

        this.repository=repository;
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

            List<Movie> movieList = new ArrayList<>(repository.findAll());

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
            Sort sort = new Sort(Sort.Direction.ASC, "Genre").and(new Sort(Sort.Direction.DESC, "YearOfRelease"));
            Iterable<Movie> entities=repository.findAll(sort);
            return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public synchronized Optional<Movie> FindOne(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public synchronized void addEntity(Movie entity) throws MyException {

        repository.findById(entity.getId()).ifPresent(optional -> {
            throw new MyException(
                    "Movie already exists");
        });
        repository.save(entity);
    }

    @Override
    public synchronized Movie updateEntity(Movie entity) throws MyException {

        if (!repository.existsById(entity.getId()))
            throw new MyException("Movie does not exist");
        return repository.save(entity);
    }

    @Override
    public synchronized Movie deleteEntity(Long id) throws ValidatorException {
        Optional<Movie> entity = repository.findById(id);
        entity.orElseThrow(()-> new MyException("Client with that ID does not exist"));
        repository.deleteById(id);
        return entity.get();
    }

    @Override
    public synchronized Set<Movie> getAllEntities() {
        Iterable<Movie> entities = repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }
}
