package UI;
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

import repository.IRepository;
import repository.postgreSQL.ClientSQLRepository;
import repository.postgreSQL.MovieSQLRepository;
import repository.postgreSQL.RentalSQLRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String URL="jdbc:postgresql://localhost:5432/MPP";
    private static final String UserName=System.getProperty("username");
    private static final String Password=System.getProperty("password");
    public static void main(String[] args) throws SQLException {
        try {
            TCPClient client= new TCPClient();
            ClientValidator clientValidator = new ClientValidator();
            MovieValidator movieValidator = new MovieValidator();
            IRepository<Long, Movie> MovieRepository;
            RentalValidator rentalValidator = new RentalValidator();
            IRepository<Long, Rental> RentalRepository;
            ExecutorService executor= Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors());
            IRepository<Long, Client> ClientRepository = new ClientSQLRepository();
            MovieRepository=new MovieSQLRepository();
            RentalRepository=new RentalSQLRepository();
            ClientService clientService = new ClientService(ClientRepository, clientValidator,executor,client);
            MovieService movieService = new MovieService(MovieRepository, movieValidator,executor,client);
            RentalService rentalService = new RentalService(clientService, movieService, RentalRepository, rentalValidator,executor,client);
            Console console = new Console(clientService, movieService, rentalService,executor);
            console.run();
        }
        catch(MyException e)
        {
            System.out.println("File is corrupted, run aborted with message:"+e.getMessage());
        }
    }
}
