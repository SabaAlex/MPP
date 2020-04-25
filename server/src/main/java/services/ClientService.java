package services;

import model.domain.Client;
import model.exceptions.MyException;
import model.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import repository.IRepository;
import repository.Sort;
import repository.SortingRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService extends BaseService<Long, Client> implements IClientService {
    @Autowired
    private IClientService iClientService;

    public ClientService(IRepository<Long, Client> repository, Validator<Client> validator) {
        super(repository, validator, "Client");
    }

    @Override
    public synchronized Set<Client> filterEntitiesField(String field) {
        Iterable<Client> clients=repository.findAll();
        Set<Client> filteredClients=new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client->!(client.getLastName().contains(field) || client.getFirstName().contains(field)) );
        return filteredClients;
    }

    @Override
    public synchronized List<Client> statEntities(String... fields) {
        if (fields.length != 0)
            throw new MyException("Something went wrong!");
        return StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList())
                    .stream()
                    .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                    .collect(Collectors.toList());
    }

    public synchronized List<Client> getAllEntitiesSorted() {
            if(repository instanceof SortingRepository)
            {
                Sort sort = new Sort("FirstName","LastName").and(new Sort(Sort.Direction.DESC, "Age"));
                sort.setClassName("Client");
                Iterable<Client> entities=((SortingRepository<Long, Client>) repository).findAll(sort);
                return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
            }
            throw new MyException("This is not A SUPPORTED SORTING REPOSITORY");

    }
}
