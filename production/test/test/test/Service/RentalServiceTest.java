package Service;

import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.validators.Validator;
import model.validators.ClientValidator;
import model.validators.MovieValidator;
import model.validators.RentalValidator;
import org.junit.Before;
import org.junit.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class RentalServiceTest {

    Validator<Client> ClientValidator;
    IRepository<Long, Client> clients;
    ClientService clientService;
    List<Client> clientArrayList;
    Validator<Movie> MovieValidator;
    IRepository<Long, Movie> movies;
    MovieService movieService;
    List<Movie> movieArrayList;
    Validator<Rental> RentalValidator;
    IRepository<Long,Rental> rentals;
    RentalService rentalService;
    List<Rental> rentalArrayList;

    int startInterval;
    int endInterval;

    @Before
    public void setUp() throws Exception {

        clients=new InMemoryRepository<Long, Client>();
        ClientValidator = new ClientValidator();
        clientService = new ClientService(clients,ClientValidator);
        clientArrayList = new ArrayList<>();

        startInterval = 1;
        endInterval = 21;

        for (int i = startInterval; i < endInterval; i++){
            Client client = new Client((long) i,"f" + Integer.toString(i),"l" + Integer.toString(i), i);
            clientArrayList.add(client);
            clientService.addClient(client);
        }

        MovieValidator = new MovieValidator();
        movies = new InMemoryRepository<Long, Movie>();
        movieService = new MovieService(movies,MovieValidator);
        movieArrayList = new ArrayList<>();

        for (int i = startInterval; i < endInterval; i++){
            Movie movie = new Movie((long) i,"t" + Integer.toString(i),1900 + i,"ms" + Integer.toString(i),"d" + Integer.toString(i),"g"  + Integer.toString(i));
            movieArrayList.add(movie);
            movieService.addMovie(movie);
        }
        RentalValidator = new RentalValidator();
        rentals = new InMemoryRepository<Long, Rental>();
        rentalService = new RentalService(clientService,movieService,rentals,RentalValidator);
        rentalArrayList = new ArrayList<>();
        for (int i = startInterval; i < 20; i++){
            Rental rental=new Rental((long) i,(long) i, 20L,1999,11,30);
            Rental rental2=new Rental((long) i*30,20L, (long)i,1997,11,30);
            rentalArrayList.add(rental);
            rentalArrayList.add(rental2);
            rentalService.addRental(rental);
            rentalService.addRental(rental2);
        }

    }

    public long length(Iterable<Rental> clients)
    {
        return StreamSupport.stream(clients.spliterator(), false).count();
    }

    @Test
    public void addRental() {
        assertEquals("Length should be " + Integer.toString(rentalArrayList.size()) + " ", length(rentalService.getAllRentals()), rentalArrayList.size());

        for (int i = startInterval; i < endInterval; i++){
            rentalService.addRental(new Rental((long)i+1000 ,(long)i,(long)i ,1990,2,4));
        }

        assertEquals("Length should be " + Integer.toString(58) + " ", length(rentalService.getAllRentals()), 58);
    }

    @Test
    public void updateRental() throws MyException {
        Rental rental=new Rental(540L,10L,20L,1980,12,1);
        try {
            rentalService.updateRental(rental);
        }
        catch (Exception e){
            throw new MyException("Rental with that id not found");
        }

        Optional<Rental> opt = rentals.findOne(rental.getId());

        opt.orElseThrow(()-> new MyException("No rental with that id found"));
        assertEquals("Rental day should be 1 ",opt.get().getDay(), 1);

    }

    @Test(expected = MyException.class)
    public void updateRentalException() throws Exception,Throwable {
        rentalService.updateRental(new Rental(-1L, 2L, 1L, 2000,11,1));
    }

    @Test
    public void deleteClients() {
        clientArrayList.forEach(i -> rentalService.DeleteClientRentals(i.getId()));

        assertEquals("Length should be 0 ", length(rentalService.getAllRentals()), 0);
    }

    @Test
    public void deleteMovies() {
        for (int i = startInterval; i < endInterval; i++){
            rentalService.DeleteMovieRentals((long)i);
        }

        assertEquals("Length should be 0 ", length(rentalService.getAllRentals()), 0);
    }
    @Test
    public void deleteRental()
    {
        rentalService.deleteRental(1L);
        assertEquals("Lenght should be 39",length(rentalService.getAllRentals()),37);
    }

    @Test(expected = MyException.class)
    public void deleteClientException() throws Exception,Throwable {
        rentalService.deleteRental(-1L);
    }

    @Test
    public void getAllRentals() {
        assertEquals("Length should be " + Integer.toString(rentalArrayList.size()) + " ",length(rentalService.getAllRentals()),rentalArrayList.size());
    }

    @Test
    public void filterRentlasByYear() {
        assertEquals("Length should be 20",length(rentalService.filterRentalsByYear(1999)),19);
        assertEquals("Length should be 20",length(rentalService.filterRentalsByYear(1997)),19);
        assertEquals("Length should be 0 ",length(rentalService.filterRentalsByYear(2000)), 0);
    }

    @Test
    public void Stats() {
        assertEquals("The year 1915 has 1 rental for clients aged 15 or higher ",rentalService.statMostRentedMovieReleasedThatYearRentalsByClientsAgedMoreThan(1915,15).size(),1);

    }
}