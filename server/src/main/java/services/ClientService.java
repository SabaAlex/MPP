package services;

import model.domain.Client;
import model.exceptions.MyException;
import model.validators.Validator;
import repository.IRepository;
import repository.Sort;
import repository.SortingRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService extends BaseService<Long, Client> {
    public ClientService(IRepository<Long, Client> repository, Validator<Client> validator, ExecutorService executor) {
        super(repository, validator, "Client",executor);
    }

    @Override
    public CompletableFuture<Set<Client>> filterEntitiesField(String field) {
        return CompletableFuture.supplyAsync(() -> {Iterable<Client> clients=repository.findAll();
            Set<Client> filteredClients=new HashSet<>();
            clients.forEach(filteredClients::add);
            filteredClients.removeIf(client->!(client.getLastName().contains(field) || client.getFirstName().contains(field)) );
            return filteredClients;
            }, executorService);
    }

    @Override
    public CompletableFuture<List<Client>> statEntities(String... fields) {
        return CompletableFuture.supplyAsync(() -> {
            if (fields.length != 0)
                throw new MyException("Something went wrong!");
            return StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList())
                .stream()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .collect(Collectors.toList());}, executorService);
    }

    public CompletableFuture<List<Client>> getAllEntitiesSorted() {
        return CompletableFuture.supplyAsync(() -> {
            if(repository instanceof SortingRepository)
            {
                Sort sort = new Sort("FirstName","LastName").and(new Sort(Sort.Direction.DESC, "Age"));
                sort.setClassName("Client");
                Iterable<Client> entities=((SortingRepository<Long, Client>) repository).findAll(sort);
                return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
            }
            throw new MyException("This is not A SUPPORTED SORTING REPOSITORY");
        }, executorService);
    }
}
