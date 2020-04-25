package repository.xml;

import model.domain.BaseEntity;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import repository.InMemoryRepository;
import repository.SavesToFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class XMLRepository<ID extends Serializable, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> implements SavesToFile {

    private String fileName;
    private Validator<T> validator;
    private String elementsTag;
    private String elementTag;
    private String elementIDTag;

    public String getElementIDTag() {
        return elementIDTag;
    }

    public String getElementTag() {
        return elementTag;
    }

    protected XMLRepository(Validator<T> validator, String fileName, String elementsTag, String elementTag, String elementIDTag) {
        this.validator = validator;
        this.fileName = fileName;
        this.elementsTag = elementsTag;
        this.elementTag = elementTag;
        this.elementIDTag = elementIDTag;

        loadData();
    }

    private void loadData() {
        try {
            org.w3c.dom.Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();

            NodeList children = root.getChildNodes();

            for (int i = 0; i < children.getLength(); i++)
            {
                Node entityNode = children.item(i);

                if(entityNode instanceof Element)
                {
                    T entity = createEntityFromElement((Element)entityNode);
                    try {
                        Iterable<T> entities = super.findAll();
                        Set<T> filteredRentals = StreamSupport.stream(entities.spliterator(),false).collect(Collectors.toSet());
                        filteredRentals
                                .stream()
                                .filter(this.filterPredicate(entity))
                                .findFirst().ifPresent(optional->{throw new MyException("Error loading file " + this.fileName);});
                        validator.validate(entity);
                        super.save(entity);
                    } catch (ValidatorException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException | ParserConfigurationException | SAXException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Optional<T> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveEntity(entity);
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(ID id) {
        Optional<T> optional = super.delete(id);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        deleteEntity(id);
        return optional;
    }

    @Override
    public Optional<T> update(T entity){
        Optional<T> optional = super.update(entity);
        if (!optional.isPresent()) {
            return optional;
        }
        updateEntity(entity);
        return Optional.empty();
    }

    private void updateEntity(T entity) {
        try {
            org.w3c.dom.Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            NodeList nodes = document.getElementsByTagName(this.elementTag);

            for (int i = 0; i < nodes.getLength(); i++) {
                Element entityElement = (Element)nodes.item(i);

                String entityToBeUpdatedId = entityElement.getAttribute(this.elementIDTag);
                System.out.println(entityToBeUpdatedId);
                System.out.println(entity.getId());
                if (entityToBeUpdatedId.equals(entity.getId().toString())) {
                    entityElement.getParentNode().replaceChild(entityToNode(entity, document), entityElement).getTextContent();
                }
            }

            Transformer transformer = TransformerFactory.
                    newInstance().
                    newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(){
        Iterable<T> entityList = super.findAll();
        try {
            org.w3c.dom.Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();

            Element root = document.createElement(elementsTag);
            document.appendChild(root);
            entityList.forEach(entity -> {
                Node entityNode = entityToNode(entity, document);

                root.appendChild(entityNode);;
            });

            Transformer transformer = TransformerFactory.
                    newInstance().
                    newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
        }
        catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private void deleteEntity(ID entityId) {
        try {
            org.w3c.dom.Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            NodeList nodes = document.getElementsByTagName(this.elementTag);

            for (int i = 0; i < nodes.getLength(); i++) {
                Element entity = (Element)nodes.item(i);

                String entityToBeDeletedId = entity.getAttribute(this.elementIDTag);
                if (entityToBeDeletedId.equals(entityId.toString())) {
                    entity.getParentNode().removeChild(entity);
                }
            }

            Transformer transformer = TransformerFactory.
                    newInstance().
                    newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    protected void saveEntity(T entity) {
        try {
            org.w3c.dom.Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();

            Node entityNode = this.entityToNode(entity, document);

            root.appendChild(entityNode);

            Transformer transformer = TransformerFactory.
                    newInstance().
                    newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    protected abstract Predicate<T> filterPredicate(T entity);

    protected abstract Node entityToNode(T entity, Document document);

    protected abstract T createEntityFromElement(Element entityNode);
}
