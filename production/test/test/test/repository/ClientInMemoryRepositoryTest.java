package repository;

import model.domain.Client;
import model.exceptions.MyException;
import model.validators.ClientValidator;
import model.validators.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ClientInMemoryRepositoryTest {
    private static final Long ID = 2L;
    Validator<Client> ClientValidator;
    IRepository<Long, Client> clients;
    @Before
    public void setUp() throws Exception {
        ClientValidator= new ClientValidator();
        clients=new InMemoryRepository<Long,Client>(ClientValidator);
        Client client=new Client("c1","f1","l1",21);
        client.setId(1L);

        clients.save(client);
        Client client2=new Client("c2","f1","l2",21);
        client2.setId(2L);
        clients.save(client2);
        Client client3=new Client("c3","f3","l2",23);
        client3.setId(3L);
        clients.save(client3);
        Client client4=new Client("c4","f4","l2",21);
        client4.setId(4L);
        clients.save(client4);
        Client client5=new Client("c5","f1","l2",25);
        client.setId(5L);
        clients.save(client5);
    }

    public long lenght(Iterable<Client> clients)
    {
        return StreamSupport.stream(clients.spliterator(), false).count();
    }

    @Test
    public void testFindOne() throws MyException {

        clients.findOne(ID).orElseThrow(()-> new MyException("No client with that id"));
    }

    @Test
    public void testFindAll() throws Exception {
        assertEquals("Lenght should be 5 ",lenght(clients.findAll()),5);
    }

    @Test
    public void testSave() throws Exception {
        clients.save(new Client("c6","f5","l1",21));
        assertEquals("Lenght should be 6 ",lenght(clients.findAll()),6);
    }


    @Test
    public void testSaveException() throws Exception {
        Client clienter=new Client("c1","f5","l1",21);
        clienter.setId(3L);
        clients.save(clienter).orElseThrow(()->new MyException("Can't save"));
    }

    @Ignore
    @Test
    public void testDelete() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
    @Test
    public void testUpdate() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
   // @Test(expected = ValidatorException.class)
    public void testUpdateException() throws Exception {
        fail("Not yet tested");
    }
}