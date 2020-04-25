package model.domain;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Movie extends BaseEntity<Long> implements Serializable {
    private String title;
    private  String genre;
    private int yearOfRelease;
    private String director;
    private String mainStar;


    @Override
    public String toString() {
        return "Title='" + title + '\'' +
                ", Year Of Release=" + yearOfRelease +
                ", Main Star='" + mainStar + '\'' +
                ", Director='" + director + '\'' +
                ", Genre='" + genre + '\'' +
                ", ID='" + getId() + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return super.getId().equals(movie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Movie(){

        setId(0L);
        title = "";
        yearOfRelease = 0;
        mainStar = "";
        director = "";
        genre = "";
    }

    public Movie(Long ID, String title, int yearOfRelease ,String mainStar, String director, String genre) {
        super.setId(ID);
        this.title = title;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
        this.director = director;
        this.mainStar = mainStar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainStar() {
        return mainStar;
    }

    public void setMainStar(String mainStar) {
        this.mainStar = mainStar;
    }


}
