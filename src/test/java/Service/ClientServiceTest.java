package Service;

import model.domain.Client;
import model.exceptions.MyException;
import model.validators.ClientValidator;
import model.validators.Validator;
import org.junit.Before;
import org.junit.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class ClientServiceTest {

    Validator<Client> ClientValidator;
    IRepository<Long, Client> clients;
    ClientService clientService;
    List<Client> clientArrayList;

    int startInterval;
    int endInterval;

    @Before
    public void setUp() throws Exception {

        ClientValidator= new ClientValidator();
        clients=new InMemoryRepository<Long, Client>(ClientValidator);
        clientService = new ClientService(clients);
        clientArrayList = new ArrayList<>();

        startInterval = 1;
        endInterval = 21;

        IntStream.range(startInterval, endInterval)
                .peek(i -> clientArrayList.add(new Client((long) i,"f" + Integer.toString(i),"l" + Integer.toString(i), i)));

        IntStream.range(0, clientArrayList.size())
                .peek(n -> clientService.addClient(clientArrayList.get(n)));
    }

    public long length(Iterable<Client> clients)
    {
        return StreamSupport.stream(clients.spliterator(), false).count();
    }

    @Test
    public void addClient() {
        assertEquals("Length should be " + Integer.toString(clientArrayList.size()) + " ", length(clientService.getAllClients()), clientArrayList.size());
        IntStream.range(startInterval, endInterval)
                .peek(i -> clientService.addClient(new Client((long) i + 100,"f" + Integer.toString(i + 100),"l" + Integer.toString(i + 100), i + 100)));
        assertEquals("Length should be " + Integer.toString(clientArrayList.size() * 2) + " ", length(clientService.getAllClients()), clientArrayList.size() * 2);
    }

    @Test
    public void updateClient() throws MyException {
        Client client=new Client(3L,"f5","l1",21);
        try {
            clientService.updateClient(client);
        }
        catch (Exception e){
            throw new MyException("It will break");
        }

        List<Client> updatedClients = clientService.filterClientsByName("f5").stream().filter(client1 -> client1.getId() == 3L).collect(Collectors.toList());

        Optional<Client> opt = Optional.ofNullable(updatedClients.get(0));

        opt.ifPresent(optional->{throw new MyException("It will break");});

    }

    @Test(expected = MyException.class)
    public void updateClientException() throws Exception,Throwable {
        clientService.updateClient(new Client(-1L, "asd", "asd", 20));
    }

    @Test
    public void deleteClient() {
        IntStream.range(0, clientArrayList.size())
                .peek(i -> clientService.deleteClient(clientArrayList.get(i).getId()));

        assertEquals("Length should be 0 ", length(clientService.getAllClients()), 0);
    }

    @Test(expected = MyException.class)
    public void deleteClientException() throws Exception,Throwable {
        clientService.deleteClient(-1L);
    }

    @Test
    public void getAllClients() {
        assertEquals("Length should be " + Integer.toString(clientArrayList.size()) + " ",length(clientService.getAllClients()),clientArrayList.size());
    }

    @Test
    public void filterClientsByName() {
        assertEquals("Length should be " + Integer.toString(clientArrayList.size()) + " ",length(clientService.filterClientsByName("f")),clientArrayList.size());
        assertEquals("Length should be " + Integer.toString(clientArrayList.size()) + " ",length(clientService.filterClientsByName("l")),clientArrayList.size());
        assertEquals("Length should be 1 ",length(clientService.filterClientsByName("10")), 1);
    }
}
