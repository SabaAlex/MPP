//import model.domain.Client;
//import model.domain.Movie;
//import model.domain.Rental;
//import model.domain.utils.FactorySerializable;
//import model.domain.utils.FactorySerializeCollection;
//import model.exceptions.MyException;
//import model.validators.ClientValidator;
//import model.validators.MovieValidator;
//import model.validators.RentalValidator;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import repository.IRepository;
//import repository.postgreSQL.ClientSQLRepository;
//import repository.postgreSQL.MovieSQLRepository;
//import repository.postgreSQL.RentalSQLRepository;
//import services.*;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.concurrent.*;
//
///**
// * Created by radu.
// */
//public class ServerStart {
//
//    public static void main(String[] args) {
//        try {
//            AnnotationConfigApplicationContext context =
//                    new AnnotationConfigApplicationContext(
//                            "server.config"
//                    );
//
//           /// ClientService clientService = (ClientService) context.getBean(IClientService.class);
//           /// MovieService movieService = (MovieService) context.getBean(IMovieService.class);
//           /// RentalService rentalService = () context.getBean(IRentalService.class);
//
//            System.out.println("server started");
//            ExecutorService executorService = Executors.newFixedThreadPool(
//                    Runtime.getRuntime().availableProcessors()
//            );
//            try {
//                ClientValidator clientValidator = new ClientValidator();
//                IRepository<Long, Client> ClientRepository = new ClientSQLRepository();
//                ClientService clientService = new ClientService(ClientRepository, clientValidator, executorService);
//                ///clientService.loadParameters(ClientRepository, clientValidator, executorService);
//
//                MovieValidator movieValidator = new MovieValidator();
//                IRepository<Long, Movie> MovieRepository = new MovieSQLRepository();
//                MovieService movieService = new MovieService(MovieRepository, movieValidator, executorService);
//                ///movieService.loadParameters(MovieRepository, movieValidator, executorService);
//
//                RentalValidator rentalValidator = new RentalValidator();
//                IRepository<Long, Rental> RentalRepository = new RentalSQLRepository();
//                RentalService rentalService = new RentalService(clientService, movieService, RentalRepository, rentalValidator, executorService);
//                ///rentalService.loadParameters(clientService, movieService, RentalRepository, rentalValidator, executorService);
//
//                TCPServer tcpServer = new TCPServer(executorService);
//
//                tcpServer.addHandler(IService.Commands.ADD_CLIENT.getCmdMessage(), (request) -> {
//
//                    try {
//                        Client client = FactorySerializable.createClient(request.getBody());
//                        Future<Client> future = clientService.addEntity(client);
//                        Client result = future.get();
//                        return new Message("ok", "null");
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.ADD_MOVIE.getCmdMessage(), (request) -> {
//                    try {
//                        Movie movie = FactorySerializable.createMovie(request.getBody());
//                        Future<Movie> future = movieService.addEntity(movie);
//                        Movie result = future.get();
//
//                        return new Message("ok", "null");
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.ADD_RENTAL.getCmdMessage(), (request) -> {
//                    try {
//                        Rental rental = FactorySerializable.createRental(request.getBody());
//                        Future<Rental> future = rentalService.addEntity(rental);
//                        Rental result = future.get();
//                        return new Message("ok", "null");
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.UPDATE_CLIENT.getCmdMessage(), (request) -> {
//                    try {
//                        Client client = FactorySerializable.createClient(request.getBody());
//                        Future<Client> future = clientService.updateEntity(client);
//                        Client result = future.get();
//
//                        return new Message("ok", FactorySerializable.toStringEntity(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.UPDATE_MOVIE.getCmdMessage(), (request) -> {
//                    try {
//                        Movie movie = FactorySerializable.createMovie(request.getBody());
//                        Future<Movie> future = movieService.updateEntity(movie);
//                        Movie result = future.get();
//
//                        return new Message("ok", FactorySerializable.toStringEntity(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.UPDATE_RENTAL.getCmdMessage(), (request) -> {
//
//                    try {
//                        Rental rental = FactorySerializable.createRental(request.getBody());
//                        Future<Rental> future = rentalService.updateEntity(rental);
//                        Rental result = future.get();
//
//                        return new Message("ok", FactorySerializable.toStringEntity(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.DELETE_CLIENT.getCmdMessage(), (request) -> {
//
//                    try {
//                        long id = Long.parseLong(request.getBody());
//                        Future<Client> future = clientService.deleteEntity(id);
//                        Client result = future.get();
//
//                        return new Message("ok", FactorySerializable.toStringEntity(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.DELETE_MOVIE.getCmdMessage(), (request) -> {
//
//                    try {
//                        long id = Long.parseLong(request.getBody());
//                        Future<Movie> future = movieService.deleteEntity(id);
//                        Movie result = future.get();
//
//                        return new Message("ok", FactorySerializable.toStringEntity(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.DELETE_RENTAL.getCmdMessage(), (request) -> {
//                    long id = Long.parseLong(request.getBody());
//                    Future<Rental> future = rentalService.deleteEntity(id);
//                    try {
//                        Rental result = future.get();
//
//                        return new Message("ok", FactorySerializable.toStringEntity(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.FILTER_CLIENT.getCmdMessage(), (request) -> {
//
//                    try {
//                        String field = request.getBody();
//                        Future<Set<Client>> future = clientService.filterEntitiesField(field);
//                        Set<Client> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringClients(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.FILTER_MOVIE.getCmdMessage(), (request) -> {
//
//                    try {
//                        String field = request.getBody();
//                        Future<Set<Movie>> future = movieService.filterEntitiesField(field);
//                        Set<Movie> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringMovies(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.FILTER_RENTAL.getCmdMessage(), (request) -> {
//
//                    try {
//                        String field = request.getBody();
//                        Future<Set<Rental>> future = rentalService.filterEntitiesField(field);
//                        Set<Rental> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringRentals(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.ALL_CLIENT.getCmdMessage(), (request) -> {
//                    try {
//                        Future<Set<Client>> future = clientService.getAllEntities();
//                        Set<Client> result = future.get();
//                        System.out.println(FactorySerializeCollection.toStringClients(result));
//                        return new Message("ok", FactorySerializeCollection.toStringClients(result));
//                    } catch (MyException | InterruptedException | ExecutionException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.ALL_MOVIE.getCmdMessage(), (request) -> {
//                    try {
//                        Future<Set<Movie>> future = movieService.getAllEntities();
//                        Set<Movie> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringMovies(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.ALL_RENTAL.getCmdMessage(), (request) -> {
//
//                    try {
//                        Future<Set<Rental>> future = rentalService.getAllEntities();
//                        Set<Rental> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringRentals(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.STAT_CLIENT.getCmdMessage(), (request) -> {
//                    try {
//                        Future<List<Client>> future = clientService.statEntities();
//                        List<Client> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringClients(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.STAT_MOVIE.getCmdMessage(), (request) -> {
//                    try {
//                        Future<List<Movie>> future = movieService.statEntities();
//                        List<Movie> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringMovies(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.STAT_RENTAL.getCmdMessage(), (request) -> {
//                    try {
//                        String[] tokens = request.getBody().split(",");
//                        Future<List<Rental>> future = rentalService.statEntities(tokens[0], tokens[1]);
//                        List<Rental> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringRentals(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.SORT_CLIENT.getCmdMessage(), (request) -> {
//                    try {
//                        Future<List<Client>> future = clientService.getAllEntitiesSorted();
//                        List<Client> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringClients(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.SORT_MOVIE.getCmdMessage(), (request) -> {
//
//                    try {
//                        Future<List<Movie>> future = movieService.getAllEntitiesSorted();
//                        List<Movie> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringMovies(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.SORT_RENTAL.getCmdMessage(), (request) -> {
//                    try {
//                        Future<List<Rental>> future = rentalService.getAllEntitiesSorted();
//                        List<Rental> result = future.get();
//
//                        return new Message("ok", FactorySerializeCollection.toStringRentals(result));
//                    } catch (InterruptedException | ExecutionException | MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.DELETE_RENTAL_CLIENT.getCmdMessage(), (request) -> {
//                    try {
//                        long id = Long.parseLong(request.getBody());
//                        rentalService.DeleteClientRentals(id);
//                        return new Message("ok", "");
//                    } catch (MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//                tcpServer.addHandler(IService.Commands.DELETE_RENTAL_MOVIE.getCmdMessage(), (request) -> {
//                    try {
//                        long id = Long.parseLong(request.getBody());
//                        rentalService.DeleteMovieRentals(id);
//                        return new Message("ok", "");
//                    } catch (MyException e) {
//                        e.printStackTrace();
//                        return new Message("error", e.getMessage());//fixme: hardcoded str
//                    }
//
//                });
//
//
////        tcpServer.addHandler(HelloService.SAY_BYE, (request) -> {
////            String name = request.getBody();
////            Future<String> future = helloService.sayBye(name);
////            try {
////                String result = future.get();
////                return new Message("ok", result);
////            } catch (InterruptedException | ExecutionException e) {
////                e.printStackTrace();
////                return new Message("error", e.getMessage());
////            }
////        });
//
//                tcpServer.startServer();
//
//                executorService.shutdown();
//            } catch (RuntimeException | SQLException ex) {
//                ex.printStackTrace();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
