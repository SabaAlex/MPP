package repository;

package repository;

import model.domain.Client;
import model.domain.Movie;

import model.domain.Rental;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.print.Book;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentalXMLRepository extends InMemoryRepository<Long, Rental> {
    private String fileName;
    Validator<Rental> validator;
    public RentalXMLRepository(Validator<Rental> validator, String fileName) {
        this.validator=validator;
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {

        try {
            org.w3c.dom.Document document=  DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(fileName);

            Element root=document.getDocumentElement();

            NodeList children=root.getChildNodes();

            for (int i=0; i<children.getLength();i++)
            {
                Node rentalNode=children.item(i);

                if(rentalNode instanceof Element)
                {
                    Rental rental=createRentalFromElement((Element)rentalNode);
                    try {
                        Iterable<Rental> rentals=super.findAll();
                        Set<Rental> filteredRentals= StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
                        filteredRentals
                                .stream()
                                .filter(exists-> (exists.getClientID()==rental.getClientID()) && exists.getMovieID()==rental.getMovieID())
                                .findFirst().ifPresent(optional->{throw new MyException("Error loading file rental for that movie and client exists");});
                        validator.validate(rental);
                        super.save(rental);
                    } catch (ValidatorException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException | ParserConfigurationException | SAXException ex) {
            ex.printStackTrace();
        }
    }

    private static Rental createRentalFromElement(Element rentalElem)
    {

        Long rentalID=Long.parseLong(rentalElem.getAttribute("rentalID"));
        Node ClientIDNode=rentalElem.getElementsByTagName("clientID").item(0);
        Long ClientID=Long.parseLong(ClientIDNode.getTextContent());
        Node MovieIDNode=rentalElem.getElementsByTagName("movieID").item(0);
        long MovieID=Long.parseLong(MovieIDNode.getTextContent());
        Node YearNode=rentalElem.getElementsByTagName("year").item(0);
        int Year=Integer.parseInt(YearNode.getTextContent());
        Node DayNode=rentalElem.getElementsByTagName("day").item(0);
        int Day=Integer.parseInt(DayNode.getTextContent());
        Node MonthNode=rentalElem.getElementsByTagName("month").item(0);
        int Month=Integer.parseInt(MonthNode.getTextContent());

        return new Rental(rentalID,ClientID,MovieID,Year,Month,Day);
    }

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException {
        Optional<Rental> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private static void saveRental(Rental rental,String fileName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        org.w3c.dom.Document document=  DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(fileName);

        Element root=document.getDocumentElement();

        Node rentalNode=rentalToNode(rental,document);

        root.appendChild(rentalNode);

        //TODO save in XML

        Transformer transformer= TransformerFactory.newInstance().newTransformer();

        transformer.transform(new DOMSource(document),new StreamResult(new File(fileName)));

    }

    public static Node rentalToNode(Rental rental, Document document)
    {
        Element rentalElem=document.createElement("rental");
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
