package Service;

import UI.TCPClient;
import model.domain.Client;
import model.domain.utils.FactorySerializable;
import model.exceptions.MyException;
import model.validators.Validator;
import repository.IRepository;
import services.Message;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService extends BaseService<Long, Client> {
    public ClientService(IRepository<Long, Client> repository, Validator<Client> validator, ExecutorService executorService, TCPClient client) {
        super(repository, validator, "Client",executorService,client);
    }

    @Override
    public Set<Client> filterEntitiesField(String field) {
        Iterable<Client> clients=repository.findAll();
        Set<Client> filteredClients=new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client->!(client.getLastName().contains(field) || client.getFirstName().contains(field)) );
        return filteredClients;
    }

    @Override
    public List<Client> statEntities(String... fields) {
        if (fields.length != 0)
            throw new MyException("Something went wrong!");
        return StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList())
                .stream()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .collect(Collectors.toList());
    }
    @Override
    public Future<Client> addEntity(Client entity) {
        Message request = new Message(Commands.ADD_ENTITY.getCmdMessage(), FactorySerializable.toStringEntity((Client) entity));
        System.out.println("sending request: " + request);
        Message response = client.sendAndReceive(request);
        System.out.println("received response: " + response);
        Client client=FactorySerializable.createClient(request.getBody());
        return executorService.submit(() -> client);
    }
}
