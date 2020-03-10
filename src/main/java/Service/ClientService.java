package Service;

import model.domain.Client;

import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.ClientFileRepository;
import repository.IRepository;
import repository.MovieFileRepository;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private IRepository<Long, Client> repository;
    private Validator<Client> validator;

    public ClientService(IRepository<Long,Client> repository,Validator<Client> validator)
    {
        this.validator=validator;
        this.repository=repository;
    }
    /**
     * Calls the repository save method with a given Client Object
     *
     * @param client created client object to be passed over to the repository
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there exist already an entity with that ClientNumber
     */
    public void addClient(Client client) throws ValidatorException
    {
        validator.validate(client);
        repository.save(client).ifPresent(optional->{throw new MyException("Client already exists");});
    }

    /**
     * Calls the repository update method with a certain Client Object
     *
     * @param client created movie object to be passed over to the repository
     * @return the updated object
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be updated.
     */
    public Client updateClient(Client client) throws ValidatorException,MyException
    {
        validator.validate(client);
        return repository.update(client).orElseThrow(()-> new MyException("No client to update"));
    }

    /**
     * Given the id of a client it calls the delete method of the repository with that id
     *
     * @param id the id of the client to be deleted
     * @return the deleted Client Instance
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be deleted.
     */
    public Client deleteClient(Long id) throws ValidatorException
    {
        return repository.delete(id).orElseThrow(()-> new MyException("No client to delete"));
    }

    /**
     * Gets all the Client Instances from the repository
     *
     * @return {@code Set} containing all the Clients Instances from the repository
     */
    public Set<Client> getAllClients()
    {
        Iterable<Client> clients=repository.findAll();
        return StreamSupport.stream(clients.spliterator(),false).collect(Collectors.toSet());

    }

    /**
     * Filters all the clients by their First or Last Name
     *
     * @param name a substring of the First or Last Name of type {@code String}
     * @return {@code HashSet} containing all the Client Instances from the repository that contain the name parameter in the
     * first name or the last name
     */
    public Set<Client> filterClientsByName(String name)
    {
        Iterable<Client> clients=repository.findAll();
        Set<Client> filteredClients=new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client->!(client.getLastName().contains(name) || client.getFirstName().contains(name)) );
        return filteredClients;
    }


    public void saveToFile() {
        if (repository instanceof ClientFileRepository){
            ((ClientFileRepository)repository).saveToFile();
        }
    }
}
