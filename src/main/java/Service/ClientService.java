package Service;

import model.domain.Client;
import model.domain.validators.ValidatorException;
import repository.IRepository;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private IRepository<Long, Client> repository;

    public ClientService(IRepository<Long,Client> repository)
    {
        this.repository=repository;
    }

    public void addClient(Client client) throws ValidatorException
    {
        repository.save(client);
    }

    public Set<Client> getAllClients()
    {
        Iterable<Client> clients=repository.findAll();
        return StreamSupport.stream(clients.spliterator(),false).collect(Collectors.toSet());

    }

    public Set<Client> filterClientsByName(String name)
    {
        Iterable<Client> clients=repository.findAll();
        Set<Client> filteredClients=new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client->!(client.getlName().contains(name) || client.getfName().contains(name)) );
        return filteredClients;
    }



}
