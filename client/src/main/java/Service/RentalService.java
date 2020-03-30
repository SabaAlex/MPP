package Service;

import UI.TCPClient;
import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.IRepository;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentalService extends BaseService<Long, Rental> {

    private ClientService clientService;
    private MovieService movieService;

    public RentalService(ClientService clientService, MovieService movieService, IRepository<Long, Rental> repository, Validator<Rental> validator, ExecutorService executor, TCPClient client) {
        super(repository, validator, "Rental",executor,client);
        this.clientService = clientService;
        this.movieService = movieService;
    }

    private void checkIDs(Long ClientID, Long MovieID)
    {
        clientService.FindOne(ClientID).orElseThrow(()->new MyException("Client ID not found! "));
        movieService.FindOne(MovieID).orElseThrow(()->new MyException("Movie ID not found! "));
    }

    private void checkRentalInRepository(Rental rental){
        checkIDs(rental.getClientID(),rental.getMovieID());
        Iterable<Rental> rentals = repository.findAll();
        Set<Rental> filteredRentals=StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
        filteredRentals
                .stream()
                .filter(exists-> (Objects.equals(exists.getClientID(), rental.getClientID()))
                        && Objects.equals(exists.getMovieID(), rental.getMovieID()))
                .findFirst()
                .ifPresent(optional->{throw new MyException("Rental for that movie and client exists");});
    }

    @Override
    public Future<Rental> addEntity(Rental entity) throws ValidatorException {
        this.checkRentalInRepository(entity);
        return super.addEntity(entity);
    }

    @Override
    public Set<Rental> filterEntitiesField(String field) {
        Iterable<Rental> rentals = repository.findAll();
        Set<Rental> filteredRentals=new HashSet<>();
        rentals.forEach(filteredRentals::add);
        filteredRentals.removeIf(rental->!(rental.getYear()==Integer.parseInt(field)) );
        return filteredRentals;
    }

    @Override
    public List<Rental> statEntities(String... fields) {
        if (fields.length != 2)
            throw new MyException("Something went wrong!");

        int movie_year = Integer.parseInt(fields[0]);
        int age = Integer.parseInt(fields[1]);

        List<Client> ClientList = clientService.getAllEntities()
                .stream()
                .filter(client->client.getAge()>=age)
                .collect(Collectors.toList());
        List<Movie> MovieList = movieService.getAllEntities()
                .stream()
                .filter(movie->movie.getYearOfRelease()==movie_year)
                .collect(Collectors.toList());

        List<Rental> rentalsList = StreamSupport.stream(repository.findAll().spliterator(),false)
                .filter(rental-> ClientList.stream().anyMatch(client -> client.getId().equals(rental.getClientID())))
                .filter(rental-> MovieList.stream().anyMatch(movie -> movie.getId().equals(rental.getMovieID())))
                .collect(Collectors.toList());

        Long mostRentedMovie = Collections.max(rentalsList.stream()
                        .map(Rental::getMovieID)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                ,
                Comparator.comparingLong(Map.Entry::getValue))
                .getKey();

        return rentalsList.stream()
                .filter(rental -> rental.getMovieID().equals(mostRentedMovie))
                .collect(Collectors.toList());
    }

    public void DeleteClientRentals(Long id)
    {
        Iterable<Rental> rentals=repository.findAll();
        Set<Rental> filteredRentals= StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
        filteredRentals
                .stream()
                .filter(toDeleteRentals-> (toDeleteRentals.getClientID().equals(id)))
                .forEach(toDelete-> repository.delete(toDelete.getId())
                );

    }
    public void DeleteMovieRentals(Long id)
    {
        Iterable<Rental> rentals = repository.findAll();
        Set<Rental> filteredRentals = StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
        filteredRentals
                .stream()
                .filter(toDeleteRentals-> toDeleteRentals.getMovieID().equals(id))
                .forEach(toDelete-> repository.delete(toDelete.getId())
                );
    }
}
