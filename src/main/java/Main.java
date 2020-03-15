import Service.ClientService;
import Service.MovieService;
import Service.RentalService;
import UI.Console;
import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.ClientValidator;
import model.validators.MovieValidator;
import model.validators.RentalValidator;
import model.validators.Validator;
import repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("projectResources\\settings.txt");

        try {
            List<String> lines = Files.readAllLines(path);
            String[] client = lines.get(0).split(",");
            String[] movie = lines.get(1).split(",");
            String[] rental = lines.get(2).split(",");
            ClientValidator clientValidator = new ClientValidator();
            IRepository<Long, Client> ClientRepository;
            MovieValidator movieValidator = new MovieValidator();
            IRepository<Long, Movie> MovieRepository;
            RentalValidator rentalValidator = new RentalValidator();
            IRepository<Long, Rental> RentalRepository;
            if (client[1].equals(".txt")) {
                ClientRepository = new ClientFileRepository(clientValidator, Paths.get("projectResources\\Clients.txt").toString());
            } else {
                ClientRepository = new InMemoryRepository<>();
            }
            if (movie[1].equals(".txt")){
                MovieRepository = new MovieFileRepository(movieValidator, Paths.get("projectResources\\Movies.txt").toString());
            }
            else
            {
                MovieRepository = new InMemoryRepository<>();
            }
            if(rental[1].equals(".txt")) {
                RentalRepository = new RentalFileRepository(rentalValidator, Paths.get("projectResources\\Rentals.txt").toString());
            }
            else if(rental[1].equals(".xml")) {
                RentalRepository=new RentalXMLRepository(rentalValidator, Paths.get("projectResources\\Rentals.xml").toString());
            }
            else
            {
                RentalRepository=new InMemoryRepository<>();
            }
            ClientService clientService = new ClientService(ClientRepository, clientValidator);

            MovieService movieService = new MovieService(MovieRepository, movieValidator);

            RentalService rentalService = new RentalService(clientService, movieService, RentalRepository, rentalValidator);

            Console console = new Console(clientService, movieService, rentalService);

            console.run();
        }
        catch(MyException e)
        {
            System.out.println("File is corrupted, run aborted with message:"+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
