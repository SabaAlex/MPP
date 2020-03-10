package Service;

import model.domain.Client;
import model.domain.Movie;
import model.exceptions.MyException;
import model.validators.MovieValidator;
import model.validators.Validator;
import org.junit.Before;
import org.junit.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class MovieServiceTest {

    Validator<Movie> MovieValidator;
    IRepository<Long, Movie> movies;
    MovieService movieService;
    List<Movie> movieArrayList;

    int startInterval;
    int endInterval;

    @Before
    public void setUp() throws Exception {

        MovieValidator = new MovieValidator();
        movies = new InMemoryRepository<Long, Movie>(MovieValidator);
        movieService = new MovieService(movies);
        movieArrayList = new ArrayList<>();

        startInterval = 1;
        endInterval = 21;

        IntStream.range(startInterval, endInterval)
                .peek(i -> movieArrayList.add(new Movie((long) i,"t" + Integer.toString(i),1900 + i,"ms" + Integer.toString(i),"d" + Integer.toString(i),"g"  + Integer.toString(i))));

        IntStream.range(0, movieArrayList.size())
                .peek(n -> movieService.addMovie(movieArrayList.get(n)));
    }

    public long length(Iterable<Movie> movies)
    {
        return StreamSupport.stream(movies.spliterator(), false).count();
    }

    @Test
    public void addMovie() {
        assertEquals("Length should be " + Integer.toString(movieArrayList.size()) + " ", length(movieService.getAllMovies()), movieArrayList.size());
        IntStream.range(1, 21)
                .peek(i -> movieService.addMovie(new Movie((long) i + 100,"t1",1900,"ms" + Integer.toString(i + 100),"d" + Integer.toString(i + 100),"g"  + Integer.toString(i + 100))));
        assertEquals("Length should be " + Integer.toString(movieArrayList.size() * 2) + " ", length(movieService.getAllMovies()), movieArrayList.size() * 2);

    }

    @Test
    public void updateMovie() {
        Movie movie=new Movie(3L,"t8",2010,"ms5","d1","g21");
        try {
            movieService.updateMovie(movie);
        }
        catch (Exception e){
            throw new MyException("It will break");
        }

        List<Movie> updatedMovie = movieService.filterMoviesByTitle("t8").stream().filter(client1 -> client1.getId() == 3L).collect(Collectors.toList());

        Optional<Client> opt = Optional.ofNullable(updatedMovie.get(0));

        opt.ifPresent(optional->{throw new MyException("It will break");});
    }

    @Test(expected = MyException.class)
    public void updateMovieException() throws Exception,Throwable {
        movieService.updateMovie(new Movie(-1L,"t8",2010,"ms5","d1","g21"));
    }

    @Test
    public void deleteMovie() {
        IntStream.range(0, movieArrayList.size())
                .peek(i -> movieService.deleteMovie(movieArrayList.get(i).getId()));

        assertEquals("Length should be 0 ", length(movieService.getAllMovies()), 0);
    }

    @Test(expected = MyException.class)
    public void deleteMovieException() throws Exception,Throwable {
        movieService.deleteMovie(-1L);
    }

    @Test
    public void getAllMovies() {
        assertEquals("Length should be " + Integer.toString(movieArrayList.size()) + " ",length(movieService.getAllMovies()),movieArrayList.size());
    }

    @Test
    public void filterMoviesByTitle() {
        assertEquals("Length should be " + Integer.toString(movieArrayList.size()) + " ",length(movieService.filterMoviesByTitle("t")),movieArrayList.size());
        assertEquals("Length should be 1 ",length(movieService.filterMoviesByTitle("10")), 1);
    }
}
