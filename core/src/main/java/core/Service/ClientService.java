package core.Service;

import core.model.domain.Client;
import core.model.exceptions.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.repository.postgreSQL.jpa.ClientJPARepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService extends BaseService<Long, Client> implements IClientService {
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    protected ClientJPARepository repository;

    public ClientService(ClientJPARepository repository) {
        this.repository=repository;
        super.serviceClassName = "Client";
        super.repository = repository;
        super.logger = log;
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
            Sort sort = new Sort(Sort.Direction.ASC, "FirstName","LastName").and(new Sort(Sort.Direction.DESC, "Age"));
            Iterable<Client> entities=repository.findAll(sort);
            return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toList());
    }

}
