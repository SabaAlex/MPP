package repository;

import model.domain.Client;
import model.domain.Movie;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.ClientValidator;
import model.validators.MovieValidator;
import model.validators.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class MoviesInMemoryRepositoryTest {
    private static final Long ID = 2L;
    Validator<Movie> Movie_Validator;
    IRepository<Long, Movie> movies;
    @Before
    public void setUp() throws Exception {
        Movie_Validator= new MovieValidator();
        movies=new InMemoryRepository<Long,Movie>(Movie_Validator);
        Movie movie=new Movie("c1","t1",2000,"ms1","d1","g1");
        movie.setId(1L);

        movies.save(movie);
        Movie movie2=new Movie("m2","f1",2000,"ms2","d1","g1");
        movie2.setId(2L);
        movies.save(movie2);
        Movie movie3=new Movie("m3","t3",2001,"ms2","d4","g23");
        movie3.setId(3L);
        movies.save(movie3);
        Movie movie4=new Movie("m4","t4",2000,"ms2","d5","g21");
        movie4.setId(4L);
        movies.save(movie4);
        Movie movie5=new Movie("m5","t1",2005,"ms2","d5","g25");
        movie5.setId(5L);
        movies.save(movie5);
    }

    public long length(Iterable<Movie> movies)
    {
        return StreamSupport.stream(movies.spliterator(), false).count();
    }

    @Test
    public void testFindOne() throws MyException {

        movies.findOne(ID).orElseThrow(()-> new MyException("No client with that id"));
    }

    @Test
    public void testFindAll() throws Exception {
        assertEquals("Length should be 5 ",length(movies.findAll()),5);
    }

    @Test
    public void testSave() throws Exception {
        movies.save(new Movie("m6","t5",2009,"ms1","d6","g21"));
        assertEquals("Lenght should be 6 ",length(movies.findAll()),6);
    }


    @Test(expected = MyException.class)
    public void testSaveException() throws Exception {
        Movie movie=new Movie("m1","t8",2010,"ms5","d1","g21");
        movie.setId(3L);
        Optional<Movie> opt = movies.save(movie);
        opt.ifPresent(optional->{throw new MyException("It will break");});
    }

    @Ignore
    @Test
    public void testDelete() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
    @Test
    public void testUpdate() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
    @Test(expected = MyException.class)
    public void testUpdateException() throws Exception {
        fail("Not yet tested");
    }
}