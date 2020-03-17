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
        movies=new InMemoryRepository<Long,Movie>();
        Movie movie=new Movie(1l,"t1",2000,"ms1","d1","g1");
        movies.save(movie);
        Movie movie2=new Movie(2L,"f1",2000,"ms2","d1","g1");
        movies.save(movie2);
        Movie movie3=new Movie(3L,"t3",2001,"ms2","d4","g23");
        movies.save(movie3);
        Movie movie4=new Movie(4L,"t4",2000,"ms2","d5","g21");
        movies.save(movie4);
        Movie movie5=new Movie(5L,"t1",2005,"ms2","d5","g25");
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
        movies.save(new Movie(7l,"t5",2009,"ms1","d6","g21"));
        assertEquals("Lenght should be 6 ",length(movies.findAll()),6);
    }


    @Test(expected = MyException.class)
    public void testSaveException() throws Exception {
        Movie movie=new Movie(3L,"t8",2010,"ms5","d1","g21");
        Optional<Movie> opt = movies.save(movie);
        opt.ifPresent(optional->{throw new MyException("It will break");});
    }


    @Test
    public void testDelete() throws Exception,Throwable {
        movies.delete(ID).orElseThrow(()->{throw new MyException("It will break");});
        assertEquals("Lengths should be equal",length(movies.findAll()),4);
    }
    @Test(expected=MyException.class)
    public void testDeleteException() throws MyException ,Throwable{
        movies.delete(7L).orElseThrow(()->{throw new MyException("It should break");});

    }
    @Test
    public void testUpdate() throws MyException,Throwable {
        Movie movie=new Movie(3L,"title",2000,"ms5","d1","g22");
        Optional<Movie> opt = movies.update(movie);
        opt.orElseThrow(()->{throw new MyException("It will break");});
        Movie updated=movies.findOne(3L).orElseThrow(()->{throw new MyException("It will break");});
        assertEquals("Title should be equal",updated.getTitle(),"title");
    }

    @Test(expected = MyException.class)
    public void testUpdateException() throws Exception,Throwable {
        Movie movie=new Movie(7L,"t",2000,"f5","l1","21");
        Optional<Movie> opt = movies.update(movie);
        opt.orElseThrow(()->{throw new MyException("No client with that name");});
    }
}
