package services;

import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.IRepository;
import repository.Sort;
import repository.SortingRepository;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentalService extends BaseService<Long, Rental> {

    private ClientService clientService;
    private MovieService movieService;

    public RentalService(ClientService clientService, MovieService movieService, IRepository<Long, Rental> repository, Validator<Rental> validator, ExecutorService executor) {
        super(repository, validator, "Rental",executor);
        this.clientService = clientService;
        this.movieService = movieService;
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
    public synchronized CompletableFuture<Rental> addEntity(Rental entity) throws ValidatorException {
        this.checkRentalInRepository(entity);
        return super.addEntity(entity);
    }

    @Override
    public synchronized CompletableFuture<Set<Rental>> filterEntitiesField(String field) {
        return CompletableFuture.supplyAsync(() ->{
            Iterable<Rental> rentals = repository.findAll();
            Set<Rental> filteredRentals=new HashSet<>();
            rentals.forEach(filteredRentals::add);
            filteredRentals.removeIf(rental->!(rental.getYear()==Integer.parseInt(field)) );
            return filteredRentals;
        }, executorService);

    }

    @Override
    public synchronized CompletableFuture<List<Rental>> statEntities(String... fields) {
        return CompletableFuture.supplyAsync(() -> {
            if (fields.length != 2)
                throw new MyException("Something went wrong!");

            int movie_year = Integer.parseInt(fields[0]);
            int age = Integer.parseInt(fields[1]);

            try {
                List<Client> ClientList = clientService.getAllEntities().get()
                        .stream()
                        .filter(client->client.getAge()>=age)
                        .collect(Collectors.toList());
                List<Movie> MovieList = movieService.getAllEntities().get()
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
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            return null;
        }, executorService);
    }

    @Override
    public synchronized CompletableFuture<Rental> updateEntity(Rental entity) throws MyException {
        return CompletableFuture.supplyAsync(()-> {
            Optional<Rental> found_rental = repository.findOne(entity.getId());
            found_rental.orElseThrow(() -> new MyException("No Rental to update"));
            Long ClientID = found_rental.get().getClientID();
            Long MovieID = found_rental.get().getMovieID();
            entity.setClientID(ClientID);
            entity.setMovieID(MovieID);
            super.validator.validate(entity);
            return repository.update(entity).orElseThrow(() -> new MyException("No Rental to update"));
        },executorService);
        }

    @Override
    public synchronized CompletableFuture<List<Rental>> getAllEntitiesSorted() {

        return CompletableFuture.supplyAsync(() -> {
            if(repository instanceof SortingRepository)
            {
                Sort sort = new Sort( "Day").and(new Sort(Sort.Direction.DESC, "Month"));
                sort.setClassName("Rental");
                Iterable<Rental> entities=((SortingRepository<Long, Rental>) repository).findAll(sort);
                return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
            }
            throw new MyException("This is not A SUPPORTED SORTING REPOSITORY");
        }, executorService);
    }

    public synchronized void DeleteClientRentals(Long id) {
        CompletableFuture.runAsync(() -> {
            Iterable<Rental> rentals=repository.findAll();
            Set<Rental> filteredRentals= StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
            filteredRentals
                    .stream()
                    .filter(toDeleteRentals-> (toDeleteRentals.getClientID().equals(id)))
                    .forEach(toDelete-> repository.delete(toDelete.getId())
                    );
        }, executorService);

    }

    public synchronized void DeleteMovieRentals(Long id) {
        CompletableFuture.runAsync(() -> {
            Iterable<Rental> rentals = repository.findAll();
            Set<Rental> filteredRentals = StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
            filteredRentals
                    .stream()
                    .filter(toDeleteRentals-> toDeleteRentals.getMovieID().equals(id))
                    .forEach(toDelete-> repository.delete(toDelete.getId())
                    );
        }, executorService);
    }
}
