package Service;

import UI.TCPClient;
import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.domain.utils.FactorySerializable;
import model.domain.utils.FactorySerializeCollection;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.IRepository;
import services.Message;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentalService extends BaseService<Long, Rental> {

    private ClientService clientService;
    private MovieService movieService;

    public RentalService(ClientService clientService, MovieService movieService, IRepository<Long, Rental> repository, Validator<Rental> validator, ExecutorService executor, TCPClient client) {
        super(repository, validator, "Rental",executor,client);
        this.clientService = clientService;
        this.movieService = movieService;
    }



    @Override
    public CompletableFuture<Set<Rental>> filterEntitiesField(String field) {

        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.FILTER_RENTAL.getCmdMessage(),field);
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Rental> rentals= FactorySerializeCollection.createRentals(response.getBody());
                    Set<Rental> rental=new HashSet<>(rentals);
                    return  rental;}
                ,executorService);
    }

    @Override
    public CompletableFuture<List<Rental>> statEntities(String... fields) {

        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.STAT_RENTAL.getCmdMessage(),"");
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Rental> rentals= FactorySerializeCollection.createRentals(response.getBody());
                    List<Rental> rental=new ArrayList<>(rentals);
                    return  rental;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Rental> addEntity(Rental entity) {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.ADD_RENTAL.getCmdMessage(),FactorySerializable.toStringEntity(entity));
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Rental rental= FactorySerializable.createRental(response.getBody());
                    return  rental;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Rental> updateEntity(Rental new_entity)
    {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.UPDATE_RENTAL.getCmdMessage(),FactorySerializable.toStringEntity(new_entity));
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Rental rental= FactorySerializable.createRental(response.getBody());
                    return  rental;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Rental> deleteEntity(Long id_delete)
    {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.DELETE_RENTAL.getCmdMessage(),id_delete.toString());
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Rental rental= FactorySerializable.createRental(response.getBody());
                    return  rental;}
                ,executorService);
    }

    @Override
    public CompletableFuture<Set<Rental>> getAllEntities()
    {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.ALL_RENTAL.getCmdMessage(),"");
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Rental> rentals= FactorySerializeCollection.createRentals(response.getBody());
                    Set<Rental> rental=new HashSet<>(rentals);
                    return  rental;}
                ,executorService);
    }

    @Override
    public CompletableFuture<List<Rental>> getAllEntitiesSorted() {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.SORT_RENTAL.getCmdMessage(),"");
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Rental> rentals= FactorySerializeCollection.createRentals(response.getBody());
                    List<Rental> rental=new ArrayList<>(rentals);
                    return  rental;}
                ,executorService);
    }

    public void DeleteClientRentals(Long id)
    {
        CompletableFuture.runAsync(()->{
        Message request = new Message(Commands.DELETE_RENTAL_CLIENT.getCmdMessage(),id.toString());
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        if(response.getHeader().equals("error"))
        {
            throw new MyException(response.getBody());
        }},executorService
        );


    }
    public void DeleteMovieRentals(Long id)
    {
        CompletableFuture.runAsync(()->{
            Message request = new Message(Commands.DELETE_RENTAL_MOVIE.getCmdMessage(),id.toString());
            System.out.println("sending request: " + request);
            Message response = client.sendAndReceive(request);
            if(response.getHeader().equals("error"))
            {
                throw new MyException(response.getBody());
            }},executorService
        );
    }
}
