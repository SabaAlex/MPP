import Service.ClientService;
import Service.MovieService;
import UI.Console;
import model.domain.Client;
import model.domain.Movie;
import model.validators.ClientValidator;
import model.validators.MovieValidator;
import repository.IRepository;
import repository.InMemoryRepository;

public class Main {
    public static void main(String[] args) {
        ClientValidator clientValidator = new ClientValidator();
        IRepository<Long, Client> inMemoryRepositoryClient = new InMemoryRepository(clientValidator);

        MovieValidator movieValidator = new MovieValidator();
        IRepository<Long, Movie> inMemoryRepositoryMovie = new InMemoryRepository(movieValidator);

        ClientService clientService = new ClientService(inMemoryRepositoryClient);

        MovieService movieService = new MovieService(inMemoryRepositoryMovie);

        Console console = new Console(clientService, movieService);

        console.run();
    }
}
