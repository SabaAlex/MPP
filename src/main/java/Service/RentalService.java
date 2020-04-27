package Service;

import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.postgreSQL.jpa.RentalJPARepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentalService extends BaseService<Long, Rental> implements IRentalService {
    @Autowired
    protected RentalJPARepository repository;

    @Autowired
    private IClientService clientService;
    @Autowired
    private IMovieService movieService;

    public RentalService(IClientService clientService, IMovieService movieService, RentalJPARepository repository) {
        this.repository = repository;
        this.clientService = clientService;
        this.movieService = movieService;
        super.repository = repository;
        super.serviceClassName = "Movie";
    }

    private synchronized void checkIDs(Long ClientID, Long MovieID)
    {
        clientService.FindOne(ClientID).orElseThrow(()->new MyException("Client ID not found! "));
        movieService.FindOne(MovieID).orElseThrow(()->new MyException("Movie ID not found! "));
    }

    private synchronized void checkRentalInRepository(Rental rental){
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
    public synchronized void addEntity(Rental entity) throws ValidatorException {
        this.checkRentalInRepository(entity);
        this.addEntityToRepo(entity);
    }

    public synchronized void addEntityToRepo(Rental entity) throws MyException {
        repository.findById(entity.getId()).ifPresent(optional -> {
            throw new MyException(
                    "Rental already exists");
        });
        repository.save(entity);
    }

    @Override
    public synchronized Set<Rental> filterEntitiesField(String field) {

        Iterable<Rental> rentals = repository.findAll();
        Set<Rental> filteredRentals=new HashSet<>();
        rentals.forEach(filteredRentals::add);
        filteredRentals.removeIf(rental->!(rental.getYear()==Integer.parseInt(field)) );
        return filteredRentals;
    }

    @Override
    public synchronized List<Rental> statEntities(String... fields) {
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
            .filter(rental->ClientList.stream().filter(client->client.getId().equals(rental.getClientID())).collect(Collectors.toList()).size()>0)
            .filter(rental->MovieList.stream().filter(movie->movie.getId().equals(rental.getMovieID())).collect(Collectors.toList()).size()>0)
            .collect(Collectors.toList());
        if (rentalsList.size()==0)
            return new ArrayList<Rental>();
        else {
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

    }

    @Override
    public synchronized Rental updateEntity(Rental entity) throws MyException {
            Optional<Rental> found_rental = repository.findById(entity.getId());
            found_rental.orElseThrow(() -> new MyException("No Rental to update"));
            Long ClientID = found_rental.get().getClientID();
            Long MovieID = found_rental.get().getMovieID();
            entity.setClientID(ClientID);
            entity.setMovieID(MovieID);
            repository.findById(entity.getId()).orElseThrow(() -> new MyException("No Rental to update"));
            return repository.save(entity);
        }

    @Override
    public synchronized List<Rental> getAllEntitiesSorted() {
            Sort sort = new Sort(Sort.Direction.ASC, "Day").and(new Sort(Sort.Direction.DESC, "Month"));
            Iterable<Rental> entities=repository.findAll(sort);
            return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
    }
}
