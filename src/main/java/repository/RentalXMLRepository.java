package repository;

import model.domain.Rental;
import model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.util.function.Predicate;

public class RentalXMLRepository extends XMLRepository<Long, Rental> {

    public RentalXMLRepository(Validator<Rental> validator, String fileName) {
        super(validator, fileName, "rentals", "rental", "rentalID");
    }

    @Override
    protected Predicate<Rental> filterPredicate(Rental entity) {
        return xmlEntity -> xmlEntity.getClientID().equals(entity.getClientID()) && xmlEntity.getMovieID().equals(entity.getMovieID());
    }

    @Override
    protected Rental createEntityFromElement(Element entityNode) {
        Long rentalID = Long.parseLong(entityNode.getAttribute(super.getElementIDTag()));

        Node ClientIDNode = entityNode.getElementsByTagName("clientID").item(0);
        Long ClientID = Long.parseLong(ClientIDNode.getTextContent());

        Node MovieIDNode = entityNode.getElementsByTagName("movieID").item(0);
        long MovieID = Long.parseLong(MovieIDNode.getTextContent());

        Node YearNode = entityNode.getElementsByTagName("year").item(0);
        int Year = Integer.parseInt(YearNode.getTextContent());

        Node DayNode = entityNode.getElementsByTagName("day").item(0);
        int Day = Integer.parseInt(DayNode.getTextContent());

        Node MonthNode = entityNode.getElementsByTagName("month").item(0);
        int Month = Integer.parseInt(MonthNode.getTextContent());

        return new Rental(rentalID, ClientID, MovieID, Year, Month, Day);
    }

    @Override
    protected Node entityToNode(Rental rental, Document document)
    {
        Element rentalElem=document.createElement(super.getElementTag());
        rentalElem.setAttribute("rentalID",rental.getId().toString());

        Element clientIDElement=document.createElement("clientID");
        clientIDElement.setTextContent(rental.getClientID().toString());

        rentalElem.appendChild(clientIDElement);

        Element movieIDElement=document.createElement("movieID");
        movieIDElement.setTextContent(rental.getMovieID().toString());

        rentalElem.appendChild(movieIDElement);


        Element YearElement=document.createElement("year");
        YearElement.setTextContent(Integer.toString(rental.getYear()));

        rentalElem.appendChild(YearElement);

        Element MonthElement=document.createElement("month");
        MonthElement.setTextContent(Integer.toString(rental.getMonth()));

        rentalElem.appendChild(MonthElement);

        Element DayElement=document.createElement("day");
        DayElement.setTextContent(Integer.toString(rental.getDay()));

        rentalElem.appendChild(DayElement);

        return rentalElem;
    }


}
