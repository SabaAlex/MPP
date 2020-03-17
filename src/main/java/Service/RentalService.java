package Service;

import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentalService {

    private ClientService clientServ;
    private MovieService movieServ;
    private IRepository<Long, Rental> RentalRepository;
    private Validator<Rental> validator;
    public RentalService(ClientService clientServ,MovieService movieServ,IRepository<Long,Rental> RentalRepository,Validator<Rental> validator )

    {
        this.validator=validator;
        this.clientServ=clientServ;
        this.movieServ=movieServ;
        this.RentalRepository=RentalRepository;
    }
    public void checkIDs(Long ClientID,Long MovieID)
    {
        clientServ.FindOne(ClientID).orElseThrow(()->new MyException("Client ID not found! "));
        movieServ.FindOne(MovieID).orElseThrow(()->new MyException("Movie ID not found! "));
    }
    /**
     * Calls the repository save method with a given Rental Object
     *
     * @param rental created rental object to be passed over to the repository
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there exist already an entity with that ClientNumber
     */
    public void addRental(Rental rental) throws ValidatorException,MyException
    {
        checkIDs(rental.getClientID(),rental.getMovieID());
        Iterable<Rental> rentals=RentalRepository.findAll();
        Set<Rental> filteredRentals=StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
        filteredRentals
                .stream()
                .filter(exists-> (exists.getClientID()==rental.getClientID()) && exists.getMovieID()==rental.getMovieID())
                .findFirst().ifPresent(optional->{throw new MyException("Rental for that movie and client exists");});

        validator.validate(rental);
        RentalRepository.save(rental).ifPresent(optional->{throw new MyException("Rental already exists");});
    }

    /**
     * Calls the repository update method with a certain Client Object
     *
     * @param rental created rental object to be passed over to the repository
     * @return the updated object
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be updated.
     */
    public Rental updateRental(Rental rental) throws ValidatorException,MyException
    {

        Optional<Rental> found_rental=RentalRepository.findOne(rental.getId());
        found_rental.orElseThrow(()-> new MyException("No Rental to update"));
        Long ClientID=found_rental.get().getClientID();
        Long MovieID=found_rental.get().getMovieID();
        rental.setClientID(ClientID);
        rental.setMovieID(MovieID);
        validator.validate(rental);
        return RentalRepository.update(rental).orElseThrow(()-> new MyException("No Rental to update"));
    }

    /**
     * Given the id of a rental it calls the delete method of the repository with that id
     *
     * @param id the id of the rental to be deleted
     * @return the deleted Client Instance
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be deleted.
     */
    public Rental deleteRental(Long id) throws ValidatorException
    {
        return RentalRepository.delete(id).orElseThrow(()-> new MyException("No rental to delete"));
    }

    /**
     * Gets all the Rentals Instances from the repository
     *
     * @return {@code Set} containing all the Clients Instances from the repository
     */



    public Set<Rental> getAllRentals()
    {
        Iterable<Rental> rentals=RentalRepository.findAll();
        return StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());

    }



    public void DeleteClientRentals(Long id)
    {
        Iterable<Rental> rentals=RentalRepository.findAll();
        Set<Rental> filteredRentals=StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
        filteredRentals
                .stream()
                .filter(toDeleteRentals-> (toDeleteRentals.getClientID() ==id))
                .forEach(toDelete->{RentalRepository.delete(toDelete.getId());}
                );

    }
    public void DeleteMovieRentals(Long id)
    {
        Iterable<Rental> rentals=RentalRepository.findAll();
        Set<Rental> filteredRentals=StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
        filteredRentals
                .stream()
                .filter(toDeleteRentals-> !((toDeleteRentals.getMovieID()) == id))
                .forEach(toDelete->{RentalRepository.delete(toDelete.getId());}
                );
    }
    /**
     * Filters all the rentals by their Years
     *
     * @param year a substring of the year of type {@code String}
     * @return {@code HashSet} containing all the Rental Instances from the repository that have been rented in that year
     *
     */
    public Set<Rental> filterRentalsByYear(int year)
    {
        Iterable<Rental> rentals= RentalRepository.findAll();
        Set<Rental> filteredRentals=new HashSet<>();
        rentals.forEach(filteredRentals::add);
        filteredRentals.removeIf(rental->!(rental.getYear()==year) );
        return filteredRentals;
    }

    public Set<Rental> statMostRentedMovieReleasedThatYearRentalsByClientsAgedMoreThan(int movie_year,int age){
        List<Client> ClientList=clientServ.getAllClients().stream().filter(client->client.getAge()>=age).collect(Collectors.toList());
        List<Movie> MovieList=movieServ.getAllMovies().stream().filter(movie->movie.getYearOfRelease()==movie_year).collect(Collectors.toList());
        List<Rental> rentalsList = StreamSupport.stream(RentalRepository.findAll().spliterator(),false)
                .filter(rental->ClientList.stream().filter(client->client.getId().equals(rental.getClientID())).collect(Collectors.toList()).size()>0)
                .filter(rental->MovieList.stream().filter(movie->movie.getId().equals(rental.getMovieID())).collect(Collectors.toList()).size()>0)
                .collect(Collectors.toList());

        Long mostRentedMovie = Collections.max(rentalsList.stream()
                .map(Rental::getMovieID)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                ,
                Comparator.comparingLong(Map.Entry::getValue))
                .getKey();

        Set<Rental> filteredRentals = rentalsList.stream()
                .filter(rental -> rental.getMovieID().equals(mostRentedMovie))
                .collect(Collectors.toSet());

        return filteredRentals;
    }

    public void saveToFile() {
        if (RentalRepository instanceof SavesToFile){
            ((SavesToFile)RentalRepository).saveToFile();
        }
    }
}
