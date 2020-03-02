package Service;

import model.domain.Client;

import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import repository.IRepository;


import java.util.HashSet;
import java.util.Optional;
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

    public Client updateClient(Client client) throws ValidatorException,MyException
    {
        return repository.update(client).orElseThrow(()-> new MyException("No client to update"));
    }

    public Client deleteClient(Long id) throws ValidatorException
    {
        return repository.delete(id).orElseThrow(()-> new MyException("No client to delete"));
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
