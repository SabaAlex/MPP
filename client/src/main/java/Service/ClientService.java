package Service;

import UI.TCPClient;
import model.domain.Client;
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

public class ClientService extends BaseService<Long, Client> {
    public ClientService(IRepository<Long, Client> repository, Validator<Client> validator, ExecutorService executorService, TCPClient client) {
        super(repository, validator, "Client",executorService,client);
    }

    @Override
    public CompletableFuture<Set<Client>> filterEntitiesField(String field) {

        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.FILTER_CLIENT.getCmdMessage(),field);
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Client> clients= FactorySerializeCollection.createClients(response.getBody());
                    Set<Client> client=new HashSet<>(clients);
                    return  client;}
                ,executorService);
    }

    @Override
    public CompletableFuture<List<Client>> statEntities(String... fields) {

        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.STAT_CLIENT.getCmdMessage(),"");
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Client> clients= FactorySerializeCollection.createClients(response.getBody());
                    List<Client> client=new ArrayList<>(clients);
                    return  client;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Client> addEntity(Client entity) {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.ADD_CLIENT.getCmdMessage(),FactorySerializable.toStringEntity(entity));
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Client client= FactorySerializable.createClient(response.getBody());
                    return  client;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Client> updateEntity(Client new_entity)
    {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.UPDATE_CLIENT.getCmdMessage(),FactorySerializable.toStringEntity(new_entity));
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Client client= FactorySerializable.createClient(response.getBody());
                    return  client;}
                ,executorService);
    }
    @Override
    public CompletableFuture<Client> deleteEntity(Long id_delete)
    {
        return CompletableFuture.supplyAsync(()->{
                    Message request = new Message(Commands.DELETE_CLIENT.getCmdMessage(),id_delete.toString());
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Client client= FactorySerializable.createClient(response.getBody());
                    return  client;}
                ,executorService);
    }

    @Override
    public CompletableFuture<Set<Client>> getAllEntities()
    {
        return CompletableFuture.supplyAsync(()->{
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message request = new Message(Commands.ALL_CLIENT.getCmdMessage(),"");
                    System.out.println("sending request: " + request);
                    Message response = client.sendAndReceive(request);
                    System.out.println("received response: " + response);
                    if(response.getHeader().equals("error")){throw new MyException(response.getBody());
                    }
                    Collection<Client> clients= FactorySerializeCollection.createClients(response.getBody());
                    Set<Client> client=new HashSet<>(clients);
                    return  client;}
                ,executorService);
    }

    @Override
    public CompletableFuture<List<Client> > getAllEntitiesSorted() {
        return CompletableFuture.supplyAsync(()->{
        Message request = new Message(Commands.SORT_CLIENT.getCmdMessage(),"");
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        if(response.getHeader().equals("error")){throw new MyException(response.getBody());
        }
        Collection<Client> clients= FactorySerializeCollection.createClients(response.getBody());
        List<Client> client=new ArrayList<>(clients);
        return  client;}
        ,executorService);
    }
}
