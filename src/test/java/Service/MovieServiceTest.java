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

        for (int i = startInterval; i < endInterval; i++){
            Movie movie = new Movie((long) i,"t" + Integer.toString(i),1900 + i,"ms" + Integer.toString(i),"d" + Integer.toString(i),"g"  + Integer.toString(i));
            movieArrayList.add(movie);
            movieService.addMovie(movie);
        }

    }

    public long length(Iterable<Movie> movies)
    {
        return StreamSupport.stream(movies.spliterator(), false).count();
    }

    @Test
    public void addMovie() {
        assertEquals("Length should be " + Integer.toString(movieArrayList.size()) + " ", length(movieService.getAllMovies()), movieArrayList.size());

        for (int i = startInterval; i < endInterval; i++){
            Movie movie = new Movie((long) i+100,"t" + Integer.toString(i+100),1900 + i,"ms" + Integer.toString(i),"d" + Integer.toString(i),"g"  + Integer.toString(i));
            movieService.addMovie(movie);
        }
        assertEquals("Length should be " + Integer.toString(movieArrayList.size() * 2) + " ", length(movieService.getAllMovies()), movieArrayList.size() * 2);

    }

    @Test
    public void updateMovie() {
        Movie movie=new Movie(1L,"t8",2010,"ms5","d1","g21");
        try {
            movieService.updateMovie(movie);
        }
        catch (Exception e){
            throw new MyException("It will break");
        }


        Optional<Movie> opt = movies.findOne(movie.getId());

        opt.orElseThrow(()-> new MyException("No movie to update"));
    }

    @Test(expected = MyException.class)
    public void updateMovieException() throws Exception,Throwable {
        movieService.updateMovie(new Movie(-1L,"t8",2010,"ms5","d1","g21"));
    }

    @Test
    public void deleteMovie() {
        for (int i = startInterval; i < endInterval; i++){
            movieService.deleteMovie((long)i);
        }

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
