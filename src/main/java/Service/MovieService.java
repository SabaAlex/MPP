package Service;

import model.domain.Client;
import model.domain.Movie;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.IRepository;
import repository.MovieFileRepository;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService {
    private IRepository<Long, Movie> repository;
    private Validator<Movie> validator;
    public MovieService(IRepository<Long,Movie> repository,Validator<Movie> validator)
    {
        this.validator=validator;
        this.repository=repository;
    }

    /**
     * Calls the repository save method with a certain Movie Object
     *
     * @param movie created movie object to be passed over to the repository
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *              if there exist already an entity with that MovieNumber
     */
    public void addMovie(Movie movie) throws ValidatorException
    {
        validator.validate(movie);
        repository.save(movie).ifPresent(optional->{throw new MyException("Movie already exists");});
    }

    /**
     * Calls the repository update method with a certain Movie Object
     *
     * @param movie created movie object to be passed over to the repository
     * @return the updated object
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be updated.
     */
    public Movie updateMovie(Movie movie) throws ValidatorException, MyException
    {
        validator.validate(movie);
        return repository.update(movie).orElseThrow(()-> new MyException("No movie to update"));
    }

    /**
     * Given the id of a movie it calls the delete method of the repository with that id
     *
     * @param id the id of the movie to be deleted
     * @return the deleted Movie Instance
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be deleted.
     */
    public Movie deleteMovie(Long id) throws ValidatorException
    {
        return repository.delete(id).orElseThrow(()-> new MyException("No movie to delete"));
    }

    /**
     * Gets all the Movie Instances from the repository
     *
     * @return {@code Set} containing all the Movie Instances from the repository
     */
    public Set<Movie> getAllMovies()
    {
        Iterable<Movie> movies=repository.findAll();
        return StreamSupport.stream(movies.spliterator(),false).collect(Collectors.toSet());

    }

    /**
     * Filters all the movies out by title
     *
     * @param title a movie title of type {@code String}
     * @return {@code HashSet} containing all the Movie Instances from the repository that contain the title parameter in the title
     */
    public Set<Movie> filterMoviesByTitle(String title)
    {
        Iterable<Movie> movies=repository.findAll();
        Set<Movie> filteredMovies=new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie->!(movie.getTitle().contains(title)) );
        return filteredMovies;
    }


    public void saveToFile() {
        if (repository instanceof MovieFileRepository){
            ((MovieFileRepository)repository).saveToFile();
        }
    }
}
