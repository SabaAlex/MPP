package repository;

import model.domain.Client;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.ClientValidator;
import model.validators.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;
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
        Client client=new Client(1L,"c1","f1","l1",21);

        clients.save(client);
        Client client2=new Client(2L,"c2","f1","l2",21);
        clients.save(client2);
        Client client3=new Client(3L,"c3","f3","l2",23);
        clients.save(client3);
        Client client4=new Client(4L,"c4","f4","l2",21);
        clients.save(client4);
        Client client5=new Client(5L,"c5","f1","l2",25);
        clients.save(client5);
    }

    public long length(Iterable<Client> clients)
    {
        return StreamSupport.stream(clients.spliterator(), false).count();
    }

    @Test
    public void testFindOne() throws MyException {

        clients.findOne(ID).orElseThrow(()-> new MyException("No client with that id"));
    }

    @Test
    public void testFindAll() throws Exception {
        assertEquals("Length should be 5 ",length(clients.findAll()),5);
    }

    @Test
    public void testSave() throws Exception {
        clients.save(new Client(7L,"c6","f5","l1",21)).ifPresent(optional->{throw new MyException("It will break");});
        assertEquals("Length should be 6 ",length(clients.findAll()),6);
    }


    @Test(expected = MyException.class)
    public void testSaveException() throws Exception {
        Client client=new Client(3L,"c1","f5","l1",21);
        Optional<Client> opt = clients.save(client);
        opt.ifPresent(optional->{throw new MyException("It will break");});
    }

    @Test
    public void testDelete() throws MyException ,Throwable{
        clients.delete(ID).orElseThrow(()->{throw new MyException("It will break");});
        assertEquals("Lengths should be equal",length(clients.findAll()),4);
    }

    @Test
    public void testUpdate() throws MyException,Throwable {
        Client client=new Client(3L,"c1","f5","l1",21);
        Optional<Client> opt = clients.update(client);
        opt.orElseThrow(()->{throw new MyException("It will break");});
        Client updated=clients.findOne(3L).orElseThrow(()->{throw new MyException("It will break");});
        assertEquals("Number should be equal",updated.getClientNumber(),"c1");
    }

    @Ignore
    @Test(expected = MyException.class)
    public void testUpdateException() throws Exception {
        fail("Not yet tested");
    }
}