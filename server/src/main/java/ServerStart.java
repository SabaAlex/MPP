import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.domain.utils.FactorySerializable;
import model.domain.utils.FactorySerializeCollection;
import model.exceptions.MyException;
import model.validators.ClientValidator;
import model.validators.MovieValidator;
import model.validators.RentalValidator;
import repository.IRepository;
import repository.postgreSQL.ClientSQLRepository;
import repository.postgreSQL.MovieSQLRepository;
import repository.postgreSQL.RentalSQLRepository;
import services.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public class ServerStart {

    public static void main(String[] args) {
        try {

            System.out.println("server started");
            ExecutorService executorService = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors()
            );
            try {
                ClientValidator clientValidator = new ClientValidator();
                IRepository<Long, Client> ClientRepository = new ClientSQLRepository();
                ClientService clientService = new ClientService(ClientRepository, clientValidator, executorService);

                MovieValidator movieValidator = new MovieValidator();
                IRepository<Long, Movie> MovieRepository = new MovieSQLRepository();
                MovieService movieService = new MovieService(MovieRepository, movieValidator, executorService);

                RentalValidator rentalValidator = new RentalValidator();
                IRepository<Long, Rental> RentalRepository = new RentalSQLRepository();
                RentalService rentalService = new RentalService(clientService, movieService, RentalRepository, rentalValidator, executorService);

                TCPServer tcpServer = new TCPServer(executorService);

                tcpServer.addHandler(IService.Commands.ADD_CLIENT.getCmdMessage(), (request) -> {
                    System.out.println("LOOl");

                    try {
                        System.out.println("LOOl");
                        Client client = FactorySerializable.createClient(request.getBody());
                        System.out.println("LOOl3");
                        Future<Client> future = clientService.addEntity(client);
                        System.out.println("LOOl1");
                        Client result = future.get();
                        System.out.println("LOOl2");
                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.ADD_MOVIE.getCmdMessage(), (request) -> {
                    Movie movie = FactorySerializable.createMovie(request.getBody());
                    Future<Movie> future = movieService.addEntity(movie);
                    try {
                        Movie result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.ADD_RENTAL.getCmdMessage(), (request) -> {
                    Rental rental = FactorySerializable.createRental(request.getBody());
                    Future<Rental> future = rentalService.addEntity(rental);
                    try {
                        Rental result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.UPDATE_CLIENT.getCmdMessage(), (request) -> {
                    Client client = FactorySerializable.createClient(request.getBody());
                    Future<Client> future = clientService.updateEntity(client);
                    try {
                        Client result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.UPDATE_MOVIE.getCmdMessage(), (request) -> {
                    Movie movie = FactorySerializable.createMovie(request.getBody());
                    Future<Movie> future = movieService.updateEntity(movie);
                    try {
                        Movie result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.UPDATE_RENTAL.getCmdMessage(), (request) -> {
                    Rental rental = FactorySerializable.createRental(request.getBody());
                    Future<Rental> future = rentalService.updateEntity(rental);
                    try {
                        Rental result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.DELETE_CLIENT.getCmdMessage(), (request) -> {
                    long id = Long.parseLong(request.getBody());
                    Future<Client> future = clientService.deleteEntity(id);
                    try {
                        Client result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.DELETE_MOVIE.getCmdMessage(), (request) -> {
                    long id = Long.parseLong(request.getBody());
                    Future<Movie> future = movieService.deleteEntity(id);
                    try {
                        Movie result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.DELETE_RENTAL.getCmdMessage(), (request) -> {
                    long id = Long.parseLong(request.getBody());
                    Future<Rental> future = rentalService.deleteEntity(id);
                    try {
                        Rental result = future.get();

                        return new Message("ok", FactorySerializable.toStringEntity(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.FILTER_CLIENT.getCmdMessage(), (request) -> {
                    String field = request.getBody();
                    Future<Set<Client>> future = clientService.filterEntitiesField(field);
                    try {
                        Set<Client> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringClients(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.FILTER_MOVIE.getCmdMessage(), (request) -> {
                    String field = request.getBody();
                    Future<Set<Movie>> future = movieService.filterEntitiesField(field);
                    try {
                        Set<Movie> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringMovies(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.FILTER_RENTAL.getCmdMessage(), (request) -> {
                    String field = request.getBody();
                    Future<Set<Rental>> future = rentalService.filterEntitiesField(field);
                    try {
                        Set<Rental> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringRentals(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.ALL_CLIENT.getCmdMessage(), (request) -> {

                    Set<Client> future = clientService.getAll();
                    try {
                        Set<Client> result = future;
                        System.out.println("LLOLLL");
                        System.out.println(FactorySerializeCollection.toStringClients(result));
                        return new Message("ok", FactorySerializeCollection.toStringClients(result));
                    } catch (MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.ALL_MOVIE.getCmdMessage(), (request) -> {

                    try {
                        Future<Set<Movie>> future = movieService.getAllEntities();
                        Set<Movie> result = future.get();
                        System.out.println("Hereee!");

                        return new Message("ok", FactorySerializeCollection.toStringMovies(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.ALL_RENTAL.getCmdMessage(), (request) -> {
                    Future<Set<Rental>> future = rentalService.getAllEntities();
                    try {
                        Set<Rental> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringRentals(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.STAT_CLIENT.getCmdMessage(), (request) -> {
                    Future<List<Client>> future = clientService.statEntities();
                    try {
                        List<Client> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringClients(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.STAT_MOVIE.getCmdMessage(), (request) -> {
                    Future<List<Movie>> future = movieService.statEntities();
                    try {
                        List<Movie> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringMovies(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.STAT_RENTAL.getCmdMessage(), (request) -> {
                    String[] tokens = request.getBody().split(",");
                    Future<List<Rental>> future = rentalService.statEntities(tokens[0].replaceAll(" ", ""), tokens[1].replaceAll(" ", ""));
                    try {
                        List<Rental> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringRentals(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.SORT_CLIENT.getCmdMessage(), (request) -> {
                    Future<List<Client>> future = clientService.getAllEntitiesSorted();
                    try {
                        List<Client> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringClients(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.SORT_MOVIE.getCmdMessage(), (request) -> {
                    Future<List<Movie>> future = movieService.getAllEntitiesSorted();
                    try {
                        List<Movie> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringMovies(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.SORT_RENTAL.getCmdMessage(), (request) -> {
                    Future<List<Rental>> future = rentalService.getAllEntitiesSorted();
                    try {
                        List<Rental> result = future.get();

                        return new Message("ok", FactorySerializeCollection.toStringRentals(result));
                    } catch (InterruptedException | ExecutionException | MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.DELETE_RENTAL_CLIENT.getCmdMessage(), (request) -> {
                    long id = Long.parseLong(request.getBody());
                    rentalService.DeleteClientRentals(id);
                    try {
                        return new Message("ok", "");
                    } catch (MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

                tcpServer.addHandler(IService.Commands.DELETE_RENTAL_MOVIE.getCmdMessage(), (request) -> {
                    long id = Long.parseLong(request.getBody());
                    rentalService.DeleteMovieRentals(id);
                    try {
                        return new Message("ok", "");
                    } catch (MyException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });


//        tcpServer.addHandler(HelloService.SAY_BYE, (request) -> {
//            String name = request.getBody();
//            Future<String> future = helloService.sayBye(name);
//            try {
//                String result = future.get();
//                return new Message("ok", result);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//                return new Message("error", e.getMessage());
//            }
//        });

                tcpServer.startServer();

                executorService.shutdown();
            } catch (RuntimeException | SQLException ex) {
                ex.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
