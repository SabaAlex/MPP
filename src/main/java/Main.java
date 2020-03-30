import Service.ClientService;
import Service.MovieService;
import Service.RentalService;
import UI.Console;
import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.MyException;
import model.validators.ClientValidator;
import model.validators.MovieValidator;
import model.validators.RentalValidator;
import repository.*;
import repository.file.ClientFileRepository;
import repository.file.MovieFileRepository;
import repository.file.RentalFileRepository;
import repository.postgreSQL.ClientSQLRepository;
import repository.xml.ClientXMLRepository;
import repository.xml.MovieXMLRepository;
import repository.xml.RentalXMLRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final String URL="jdbc:postgresql://localhost:5432/MPP";
    private static final String UserName=System.getProperty("username");
    private static final String Password=System.getProperty("password");
    public static void main(String[] args) throws SQLException {
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
            }
            else if(client[1].equals(".xml")) {
                ClientRepository = new ClientXMLRepository(clientValidator, Paths.get("projectResources\\Clients.xml").toString()); }
            else {
                ClientRepository = new InMemoryRepository<>();
            }

            ClientRepository = new ClientSQLRepository();

            if (movie[1].equals(".txt")){
                MovieRepository = new MovieFileRepository(movieValidator, Paths.get("projectResources\\Movies.txt").toString());
            }
            else if(movie[1].equals(".xml")) {
                MovieRepository = new MovieXMLRepository(movieValidator, Paths.get("projectResources\\Movies.xml").toString());
            }
            else
            {
                MovieRepository = new InMemoryRepository<>();
            }

            if(rental[1].equals(".txt")) {
                RentalRepository = new RentalFileRepository(rentalValidator, Paths.get("projectResources\\Rentals.txt").toString());
            }
            else if(rental[1].equals(".xml")) {
                RentalRepository = new RentalXMLRepository(rentalValidator, Paths.get("projectResources\\Rentals.xml").toString());
            }
            else
            {
                RentalRepository = new InMemoryRepository<>();
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
