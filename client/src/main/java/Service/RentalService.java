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
    public Future<Set<Rental>> filterEntitiesField(String field) {
        Message request = new Message(Commands.FILTER_RENTAL.getCmdMessage(),field);
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Set<Rental> rentals= (Set)FactorySerializeCollection.createRentals(request.getBody());
        return executorService.submit(()->rentals);
    }

    @Override
    public Future<List<Rental>> statEntities(String... fields) {
        if (fields.length != 0)
            throw new MyException("Something went wrong!");
        String fieldS=Arrays.stream(fields).collect(Collectors.joining(","));
        Message request = new Message(Commands.STAT_RENTAL.getCmdMessage(),fieldS);
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        List<Rental> rentals= (List)FactorySerializeCollection.createRentals(request.getBody());
        return executorService.submit(()->rentals);
    }
    @Override
    public Future<Rental> addEntity(Rental entity) {
        Message request = new Message(Commands.ADD_RENTAL.getCmdMessage(), FactorySerializable.toStringEntity(entity));
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Rental rental=FactorySerializable.createRental(request.getBody());
        return executorService.submit(() -> rental);
    }
    @Override
    public Future<Rental> updateEntity(Rental new_entity)
    {
        Message request = new Message(Commands.UPDATE_RENTAL.getCmdMessage(), FactorySerializable.toStringEntity(new_entity));
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Rental rental=FactorySerializable.createRental(request.getBody());
        return executorService.submit(() -> rental);
    }
    @Override
    public Future<Rental> deleteEntity(Long id_delete)
    {
        Message request = new Message(Commands.UPDATE_RENTAL.getCmdMessage(),id_delete.toString());
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Rental rental=FactorySerializable.createRental(request.getBody());
        return executorService.submit(() -> rental);
    }

    @Override
    public Future<Set<Rental>> getAllEntities()
    {
        Message request = new Message(Commands.ALL_CLIENT.getCmdMessage(),"");
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Set<Rental> rentals= (Set) FactorySerializeCollection.createRentals(request.getBody());
        return executorService.submit(() -> rentals);
    }

    @Override
    public Future<List<Rental> >getAllEntitiesSorted() {
        Message request = new Message(Commands.SORT_CLIENT.getCmdMessage(),"");
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        List<Rental> rentals= (List)FactorySerializeCollection.createRentals(request.getBody());
        return executorService.submit(() -> rentals);
    }

    public void DeleteClientRentals(Long id)
    {
        Message request = new Message(Commands.DELETE_RENTAL_CLIENT.getCmdMessage(),id.toString());
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);


    }
    public void DeleteMovieRentals(Long id)
    {
        Message request = new Message(Commands.DELETE_RENTAL_MOVIE.getCmdMessage(),id.toString());
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
    }
}
