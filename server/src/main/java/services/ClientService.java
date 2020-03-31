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
        Iterable<Client> clients=repository.findAll();
        Set<Client> filteredClients=new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client->!(client.getLastName().contains(field) || client.getFirstName().contains(field)) );
        return CompletableFuture.supplyAsync(() -> filteredClients, executorService);
    }

    @Override
    public CompletableFuture<List<Client>> statEntities(String... fields) {
        if (fields.length != 0)
            throw new MyException("Something went wrong!");
        return CompletableFuture.supplyAsync(() -> StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList())
                .stream()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .collect(Collectors.toList()), executorService);
    }

    public CompletableFuture<List<Client>> getAllEntitiesSorted() {
        if(repository instanceof SortingRepository)
        {
            Sort sort = new Sort("FirstName","LastName").and(new Sort(Sort.Direction.DESC, "Age"));
            sort.setClassName("Client");
            Iterable<Client> entities=((SortingRepository<Long, Client>) repository).findAll(sort);
            List<Client> entity_set = StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
            return CompletableFuture.supplyAsync(() -> entity_set, executorService);
        }
        throw new MyException("This is not A SUPPORTED SORTING REPOSITORY");
    }

}
