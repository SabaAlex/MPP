package Service;

import UI.TCPClient;
import model.domain.Client;
import model.domain.Movie;
import model.domain.utils.FactorySerializable;
import model.domain.utils.FactorySerializeCollection;
import model.exceptions.MyException;
import model.validators.Validator;
import repository.IRepository;
import services.Message;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService extends BaseService<Long, Movie> {
    public MovieService(IRepository<Long, Movie> repository, Validator<Movie> validator, ExecutorService executor, TCPClient client) {
        super(repository, validator, "Movie",executor,client);
    }
    @Override
    public Future<Set<Movie>> filterEntitiesField(String field) {
        Message request = new Message(Commands.FILTER_MOVIE.getCmdMessage(),field);
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Set<Movie> movies= (Set)FactorySerializeCollection.createMovies(request.getBody());
        return executorService.submit(()->movies);
    }

    @Override
    public Future<List<Movie>> statEntities(String... fields) {
        Message request = new Message(Commands.STAT_MOVIE.getCmdMessage(),"");
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        List<Movie> movies= (List)FactorySerializeCollection.createMovies(request.getBody());
        return executorService.submit(()->movies);
    }
    @Override
    public Future<Movie> addEntity(Movie entity) {
        Message request = new Message(Commands.ADD_MOVIE.getCmdMessage(), FactorySerializable.toStringEntity(entity));
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Movie movie=FactorySerializable.createMovie(request.getBody());
        return executorService.submit(() -> movie);
    }
    @Override
    public Future<Movie> updateEntity(Movie new_entity)
    {
        Message request = new Message(Commands.UPDATE_MOVIE.getCmdMessage(), FactorySerializable.toStringEntity(new_entity));
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Movie movie=FactorySerializable.createMovie(request.getBody());
        return executorService.submit(() -> movie);
    }
    @Override
    public Future<Movie> deleteEntity(Long id_delete)
    {
        Message request = new Message(Commands.UPDATE_MOVIE.getCmdMessage(),id_delete.toString());
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Movie movie=FactorySerializable.createMovie(request.getBody());
        return executorService.submit(() -> movie);
    }

    @Override
    public Future<Set<Movie>> getAllEntities()
    {
        Message request = new Message(Commands.ALL_MOVIE.getCmdMessage(),"");
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Set<Movie> movies= (Set) FactorySerializeCollection.createMovies(request.getBody());
        return executorService.submit(() -> movies);
    }

    @Override
    public Future<List<Movie> >getAllEntitiesSorted() {
        Message request = new Message(Commands.SORT_MOVIE.getCmdMessage(),"");
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        List<Movie> movies= (List)FactorySerializeCollection.createMovies(request.getBody());
        return executorService.submit(() -> movies);
    }
}
