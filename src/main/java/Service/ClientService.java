package Service;

import model.domain.Client;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.postgreSQL.jpa.ClientJPARepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService implements IClientService {
    public static final Logger log = LoggerFactory.getLogger(IClientService.class);
    @Autowired
    protected ClientJPARepository repository;

    @Override
    @Transactional
    public synchronized Set<Client> getAllEntities() {
        log.trace("getAllEntities - method entered");
        Iterable<Client> entities = repository.findAll();
        log.trace("getAllEntities - method finished");
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }

    public ClientService(ClientJPARepository repository) {
        this.repository=repository;
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

    @Override
    public synchronized Optional<Client> FindOne(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public synchronized void addEntity(Client entity) throws MyException {
        log.trace("addEntity - method entered: client={}", entity);
        repository.findById(entity.getId()).ifPresent(optional -> {
            log.debug("addEntity - updated: s={}", entity);
            throw new MyException(
                    "Client already exists");
        });
        repository.save(entity);
        log.trace("addEntity - method finished");
    }

    @Override
    public synchronized Client updateEntity(Client entity) throws MyException {
        log.trace("updateEntity - method entered: client={}", entity);
        if (!repository.existsById(entity.getId()))
            throw new MyException("Client does not exist");
        log.debug("updateEntity - updated: s={}", entity);
        repository.save(entity);
        log.trace("updateStudent - method finished");
        return entity;
    }

    @Override
    public synchronized Client deleteEntity(Long id) throws ValidatorException {
        Optional<Client> entity = repository.findById(id);
        entity.orElseThrow(()-> new MyException("Client with that ID does not exist"));
        repository.deleteById(id);
        return entity.get();
    }
}
