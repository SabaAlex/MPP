package repository;

import model.domain.Movie;
import model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.function.Predicate;


public class MovieXMLRepository extends XMLRepository<Long, Movie>{

    public MovieXMLRepository(Validator<Movie> validator, String fileName) {
        super(validator, fileName, "movies", "movie", "Id");
    }

    @Override
    protected Predicate<Movie> filterPredicate(Movie entity) {
        return xmlEntity -> xmlEntity.getId().equals(entity.getId());
    }

    @Override
    protected Node entityToNode(Movie movie, Document document) {
        Element movieElem = document.createElement(super.getElementTag());
        movieElem.setAttribute("Id",movie.getId().toString());

        Element titleElement = document.createElement("title");
        titleElement.setTextContent(movie.getTitle());
        movieElem.appendChild(titleElement);

        Element yearElement = document.createElement("year");
        yearElement.setTextContent(Integer.toString(movie.getYearOfRelease()));
        movieElem.appendChild(yearElement);

        Element mainStarElement = document.createElement("mainStar");
        mainStarElement.setTextContent(movie.getMainStar());
        movieElem.appendChild(mainStarElement);

        Element directorElement = document.createElement("director");
        directorElement.setTextContent(movie.getDirector());
        movieElem.appendChild(directorElement);

        Element genreElement = document.createElement("genre");
        genreElement.setTextContent(movie.getGenre());
        movieElem.appendChild(genreElement);

        return movieElem;
    }

    @Override
    protected Movie createEntityFromElement(Element entityNode) {
        Long ID = Long.parseLong(entityNode.getAttribute(super.getElementIDTag()));

        Node titleNode = entityNode.getElementsByTagName("title").item(0);
        String title = titleNode.getTextContent();

        Node yearOfReleaseNode = entityNode.getElementsByTagName("year").item(0);
        int yearOfRelease = Integer.parseInt(yearOfReleaseNode.getTextContent());

        Node mainStarNode = entityNode.getElementsByTagName("mainStar").item(0);
        String mainStar = mainStarNode.getTextContent();

        Node directorNode = entityNode.getElementsByTagName("director").item(0);
        String director = directorNode.getTextContent();

        Node genreNode = entityNode.getElementsByTagName("genre").item(0);
        String genre = genreNode.getTextContent();

        return new Movie(ID, title, yearOfRelease, mainStar, director, genre);
    }
}
