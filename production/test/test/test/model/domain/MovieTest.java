package model.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class MovieTest {
    private static final Long ID = 1L;
    private static final Long NEW_ID = 2L;
    private static final String TITLE = "James Bond";
    private static final String GENRE = "Drama";
    private static final String DIRECTOR ="Quentin Tarantino";
    private static final String MainStar ="Brad Pitt";
    private static final int YEAR = 2003;
    private static final int NEW_YEAR = 2010;

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie(ID, TITLE,YEAR,MainStar,DIRECTOR,GENRE );
    }

    @After
    public void tearDown() throws Exception {
        movie=null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, movie.getId());
    }
    @Test
    public void testSetId() throws Exception {
        movie.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, movie.getId());
    }

    @Test
    public void testGetMainStar() throws Exception {
        assertEquals("Main star should be equal", MainStar, movie.getMainStar());
    }

    @Test
    public void testSetMainStar() throws Exception {
        movie.setMainStar(DIRECTOR);
        assertEquals("Main star should be equal", DIRECTOR, movie.getMainStar());
    }



    @Test
    public void testGetDirector() throws Exception {
        assertEquals("Director should be equal", DIRECTOR, movie.getDirector());
    }

    @Test
    public void testSetDirector() throws Exception {
        movie.setDirector(MainStar);
        assertEquals("Genre should be equal", MainStar, movie.getDirector());
    }
    @Test
    public void testGetTitle() throws Exception {
        assertEquals("Title should be equal", TITLE, movie.getTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        movie.setTitle(GENRE);
        assertEquals("Title should be equal", GENRE, movie.getTitle());
    }



    @Test
    public void testGetGenre() throws Exception {
        assertEquals("Genre should be equal", GENRE, movie.getGenre());
    }

    @Test
    public void testSetGenre() throws Exception {
        movie.setGenre(TITLE);
        assertEquals("Genre should be equal", TITLE, movie.getGenre());
    }


    @Test
    public void testGetYear() throws Exception {
        assertEquals("Years should be equal", YEAR, movie.getYearOfRelease());
    }

    @Test
    public void testSetYear() throws Exception {
        movie.setYearOfRelease(NEW_YEAR);
        assertEquals("Years should be equal", NEW_YEAR, movie.getYearOfRelease());
    }
}
