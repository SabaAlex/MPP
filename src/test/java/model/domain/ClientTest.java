package model.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class ClientTest {
    private static final Long ID = 1L;
    private static final Long NEW_ID = 2L;
    private static final String NUMBER = "C01";
    private static final String NEW_NUMBER = "C02";
    private static final String LAST_NAME = "ClientLName";
    private static final String FIRST_NAME = "ClientFName";
    private static final int AGE = 23;
    private static final int NEW_AGE = 21;

    private Client client;

    @Before
    public void setUp() throws Exception {
        client = new Client(NUMBER, FIRST_NAME,LAST_NAME, AGE);
        client.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        client=null;
    }

    @Test
    public void testGetSerialNumber() throws Exception {
        assertEquals("Serial numbers should be equal", NUMBER, client.getClientNumber());
    }

    @Test
    public void testSetSerialNumber() throws Exception {
        client.setClientNumber(NEW_NUMBER);
        assertEquals("Serial numbers should be equal", NEW_NUMBER, client.getClientNumber());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, client.getId());
    }
    @Ignore
    @Test
    public void testSetId() throws Exception {
        client.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, client.getId());
    }

    @Test
    public void testGetLastName() throws Exception {
        assertEquals("Last Name should be equal", LAST_NAME, client.getlName());
    }

    @Test
    public void testSetLastName() throws Exception {
        client.setlName(FIRST_NAME);
        assertEquals("Last Names should be equal", FIRST_NAME, client.getlName());
    }



    @Test
    public void testGetFirstName() throws Exception {
        assertEquals("First Names should be equal", FIRST_NAME, client.getfName());
    }

    @Test
    public void testSetFirstName() throws Exception {
        client.setfName(LAST_NAME);
        assertEquals("First Names should be equal", LAST_NAME, client.getfName());
    }


    @Test
    public void testGetAge() throws Exception {
        assertEquals("Ages should be equal", AGE, client.getAge());
    }

    @Test
    public void testSetGroup() throws Exception {
        client.setAge(NEW_AGE);
        assertEquals("AGEs should be equal", NEW_AGE, client.getAge());
    }
}