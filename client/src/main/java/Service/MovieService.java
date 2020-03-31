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


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService extends BaseService<Long, Movie> {
    public MovieService(IRepository<Long, Movie> repository, Validator<Movie> validator, ExecutorService executor, TCPClient client) {
        super(repository, validator, "Movie",executor,client);
    }
    @Override
    public CompletableFuture<Set<Movie>> filterEntitiesField(String field) {

        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.FILTER_CLIENT.getCmdMessage(),field);
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Movie> movies= FactorySerializeCollection.createMovies(response.getBody());
                    Set<Movie> movie=new HashSet<>(movies);
                    return  movie;}
                ,executorService);
    }

    @Override
    public CompletableFuture<List<Movie>> statEntities(String... fields) {

        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.STAT_CLIENT.getCmdMessage(),"");
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Movie> movies= FactorySerializeCollection.createMovies(response.getBody());
                    List<Movie> movie=new ArrayList<>(movies);
                    return  movie;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Movie> addEntity(Movie entity) {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.ADD_MOVIE.getCmdMessage(),FactorySerializable.toStringEntity(entity));
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Movie movie= FactorySerializable.createMovie(response.getBody());
                    return  movie;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Movie> updateEntity(Movie new_entity)
    {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.UPDATE_MOVIE.getCmdMessage(),FactorySerializable.toStringEntity(new_entity));
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Movie movie= FactorySerializable.createMovie(response.getBody());
                    return  movie;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Movie> deleteEntity(Long id_delete)
    {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.DELETE_CLIENT.getCmdMessage(),id_delete.toString());
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Movie movie= FactorySerializable.createMovie(response.getBody());
                    return  movie;}
                ,executorService);
    }

    @Override
    public CompletableFuture<Set<Movie>> getAllEntities()
    {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.ALL_MOVIE.getCmdMessage(),"");
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Movie> movies= FactorySerializeCollection.createMovies(response.getBody());
                    Set<Movie> movie=new HashSet<>(movies);
                    return  movie;}
                ,executorService);
    }

    @Override
    public CompletableFuture<List<Movie> > getAllEntitiesSorted() {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.SORT_MOVIE.getCmdMessage(),"");
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Movie> movies= FactorySerializeCollection.createMovies(response.getBody());
                    List<Movie> movie=new ArrayList<>(movies);
                    return  movie;}
                ,executorService);
    }
}
