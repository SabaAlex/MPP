import Service.ClientService;
import Service.MovieService;
import Service.RentalService;
import UI.Console;
import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.validators.ClientValidator;
import model.validators.MovieValidator;
import model.validators.RentalValidator;
import model.validators.Validator;
import repository.ClientFileRepository;
import repository.IRepository;
import repository.InMemoryRepository;

public class Main {
    public static void main(String[] args) {
        ClientValidator clientValidator = new ClientValidator();
        ///IRepository<Long, Client> inMemoryRepositoryClient = new ClientFileRepository(clientValidator, Paths.get("C:\\University\\MPP\\projectResources\\Clients.txt").toString());
        IRepository<Long, Client> inMemoryRepositoryClient = new InMemoryRepository<>(clientValidator);

        MovieValidator movieValidator = new MovieValidator();
        ///IRepository<Long, Movie> inMemoryRepositoryMovie= new MovieFileRepository(movieValidator, Paths.get("C:\\University\\MPP\\projectResources\\Clients.txt").toString());
        IRepository<Long, Movie> inMemoryRepositoryMovie = new InMemoryRepository<>(movieValidator);

        RentalValidator rentalValidator = new RentalValidator();
        IRepository<Long, Rental> inMemoryRepositoryRental = new InMemoryRepository(rentalValidator);

        ClientService clientService = new ClientService(inMemoryRepositoryClient);

        MovieService movieService = new MovieService(inMemoryRepositoryMovie);

        RentalService rentalService= new RentalService(inMemoryRepositoryClient,inMemoryRepositoryMovie,inMemoryRepositoryRental);

        Console console = new Console(clientService, movieService,rentalService);

        console.run();
    }
}
