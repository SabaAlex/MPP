package core.repository.xml;

import core.model.domain.Client;
import core.model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.function.Predicate;

public class ClientXMLRepository extends XMLRepository<Long, Client> {

    public ClientXMLRepository(Validator<Client> validator, String fileName) {
        super(validator, fileName, "clients", "client", "Id");
    }

    @Override
    protected Predicate<Client> filterPredicate(Client entity) {
        return xmlEntity -> xmlEntity.getId().equals(entity.getId());
    }

    @Override
    protected Node entityToNode(Client client, Document document) {

        Element clientElem = document.createElement(super.getElementTag());
        clientElem.setAttribute("Id",client.getId().toString());

        Element fNameElement = document.createElement("fName");
        fNameElement.setTextContent(client.getFirstName());
        clientElem.appendChild(fNameElement);

        Element lNameElement = document.createElement("lName");
        lNameElement.setTextContent(client.getLastName());
        clientElem.appendChild(lNameElement);

        Element ageElement = document.createElement("age");
        ageElement.setTextContent(Integer.toString(client.getAge()));
        clientElem.appendChild(ageElement);

        return clientElem;
    }

    @Override
    protected Client createEntityFromElement(Element entityNode) {
        Long ID = Long.parseLong(entityNode.getAttribute(super.getElementIDTag()));

        Node fNameNode = entityNode.getElementsByTagName("fName").item(0);
        String fName = fNameNode.getTextContent();

        Node lNameNode = entityNode.getElementsByTagName("lName").item(0);
        String lName = lNameNode.getTextContent();

        Node ageNode = entityNode.getElementsByTagName("age").item(0);
        int age = Integer.parseInt(ageNode.getTextContent());

        Client client = new Client(fName, lName, age);
        client.setId(ID);
        return client;
    }
}
