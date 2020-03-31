package Service;

import UI.TCPClient;
import model.domain.Client;
import model.domain.utils.FactorySerializable;
import model.domain.utils.FactorySerializeCollection;
import model.exceptions.MyException;
import model.validators.Validator;
import repository.IRepository;
import repository.Sort;
import repository.SortingRepository;
import services.Message;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService extends BaseService<Long, Client> {
    public ClientService(IRepository<Long, Client> repository, Validator<Client> validator, ExecutorService executorService, TCPClient client) {
        super(repository, validator, "Client",executorService,client);
    }

    @Override
    public Future<Set<Client>> filterEntitiesField(String field) {
        Message request = new Message(Commands.FILTER_CLIENT.getCmdMessage(),field);
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        if(response.getHeader().equals("error"))
        {
            throw new MyException(response.getBody());
        }
        Set<Client> clients= (Set)FactorySerializeCollection.createClients(request.getBody());
        return executorService.submit(()->clients);
    }

    @Override
    public Future<List<Client>> statEntities(String... fields) {
        Message request = new Message(Commands.STAT_CLIENT.getCmdMessage(),"");
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        if(response.getHeader().equals("error"))
        {
            throw new MyException(response.getBody());
        }
        List<Client> clients= (List)FactorySerializeCollection.createClients(request.getBody());
        return executorService.submit(()->clients);
    }
    @Override
    public Future<Client> addEntity(Client entity) {
        Message request = new Message(Commands.ADD_CLIENT.getCmdMessage(), FactorySerializable.toStringEntity(entity));
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        if(response.getHeader().equals("error"))
        {
            throw new MyException(response.getBody());
        }
        Client client=FactorySerializable.createClient(response.getBody());
        return executorService.submit(() -> client);
    }
    @Override
    public Future<Client> updateEntity(Client new_entity)
    {
        Message request = new Message(Commands.UPDATE_CLIENT.getCmdMessage(), FactorySerializable.toStringEntity(new_entity));
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        if(response.getHeader().equals("error"))
        {
            throw new MyException(response.getBody());
        }
        Client client=FactorySerializable.createClient(response.getBody());
        return executorService.submit(() -> client);
    }
    @Override
    public Future<Client> deleteEntity(Long id_delete)
    {
        Message request = new Message(Commands.DELETE_CLIENT.getCmdMessage(),id_delete.toString());
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        if(response.getHeader().equals("error"))
        {
            throw new MyException(response.getBody());
        }
        Client client=FactorySerializable.createClient(response.getBody());
        return executorService.submit(() -> client);
    }

    @Override
    public Future<Set<Client>> getAllEntities()
    {
        Message request = new Message(Commands.ALL_CLIENT.getCmdMessage(),"");
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        if(response.getHeader().equals("error"))
        {
            throw new MyException(response.getBody());
        }
        Collection<Client> clients= FactorySerializeCollection.createClients(response.getBody());
        Set<Client> client = new HashSet<Client>(clients);
        System.out.println("Print!");
        Future<Set<Client>> future=executorService.submit(() -> client);
        return future;
    }

    @Override
    public Future<List<Client> >getAllEntitiesSorted() {

        return  CompletableFuture.supplyAsync(() -> {
            Message request = new Message(Commands.SORT_CLIENT.getCmdMessage(),"");
            System.out.println("sending request: " + request);
            Message response = client.sendAndReceive(request);
            System.out.println("received response: " + response);
            if(response.getHeader().equals("error"))
            {
                throw new MyException(response.getBody());
            }
            Collection<Client> clients= FactorySerializeCollection.createClients(response.getBody());
            List<Client> client = new ArrayList<>(clients);
            return client;
        },executorService);
    }
}
