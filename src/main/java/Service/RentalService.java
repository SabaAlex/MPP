package Service;

import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import repository.IRepository;
import repository.RentalFileRepository;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentalService {

    private IRepository<Long, Client> ClientRepository;
    private IRepository<Long, Movie> MovieRepository;
    private IRepository<Long, Rental> RentalRepository;
    public RentalService(IRepository<Long,Client> ClientRepository, IRepository<Long,Movie> MovieRepository, IRepository<Long,Rental> RentalRepository )

    {
        this.ClientRepository=ClientRepository;
        this.MovieRepository=MovieRepository;
        this.RentalRepository=RentalRepository;
    }
    public void checkIDs(Long ClientID,Long MovieID)
    {
        ClientRepository.findOne(ClientID).orElseThrow(()->new MyException("Invalid Client ID"));
        MovieRepository.findOne(MovieID).orElseThrow(()->new MyException("Invalid Movie ID"));
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
        checkIDs(rental.getClientID(),rental.getMovieID());
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
        //TO DO
    }
    public void DeleteMovieRentals(Long id)
    {
        //TO DO
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

}
