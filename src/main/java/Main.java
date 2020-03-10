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
import repository.*;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        ClientValidator clientValidator = new ClientValidator();
        IRepository<Long, Client> inFileRepositoryClient = new ClientFileRepository(clientValidator, Paths.get("projectResources\\Clients.txt").toString());

        MovieValidator movieValidator = new MovieValidator();
        IRepository<Long, Movie> inFileRepositoryMovie= new MovieFileRepository(movieValidator, Paths.get("projectResources\\Movies.txt").toString());

        RentalValidator rentalValidator = new RentalValidator();
        IRepository<Long, Rental> inFileRepositoryRental = new RentalFileRepository(rentalValidator,Paths.get("projectResources\\Rentals.txt").toString());

        ClientService clientService = new ClientService(inFileRepositoryClient,clientValidator);

        MovieService movieService = new MovieService(inFileRepositoryMovie,movieValidator);

        RentalService rentalService= new RentalService(inFileRepositoryClient,inFileRepositoryMovie,inFileRepositoryRental,rentalValidator);

        Console console = new Console(clientService, movieService,rentalService);

        console.run();
    }
}
