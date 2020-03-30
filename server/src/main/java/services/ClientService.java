package services;

import model.domain.Client;
import model.exceptions.MyException;
import model.validators.Validator;
import repository.IRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService extends BaseService<Long, Client> {
    public ClientService(IRepository<Long, Client> repository, Validator<Client> validator, ExecutorService executor) {
        super(repository, validator, "Client",executor);
    }

    @Override
    public Future<Set<Client>> filterEntitiesField(String field) {
        Iterable<Client> clients=repository.findAll();
        Set<Client> filteredClients=new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client->!(client.getLastName().contains(field) || client.getFirstName().contains(field)) );
        return executorService.submit(() -> filteredClients);
    }

    @Override
    public Future<List<Client>> statEntities(String... fields) {
        if (fields.length != 0)
            throw new MyException("Something went wrong!");
        return executorService.submit(() ->StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList())
                .stream()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .collect(Collectors.toList()));
    }


}
