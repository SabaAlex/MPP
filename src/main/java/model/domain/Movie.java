package model.domain;

import java.util.Objects;

public class Movie extends BaseEntity<Long> {
    private String movieNumber;
    private String title;
    private  String genre;
    private int yearOfRelease;
    private String director;
    private String mainStar;

    private static long ids;

    @Override
    public String toString() {
        return "Movie Number='" + movieNumber + '\'' +
                ", Title='" + title + '\'' +
                ", Year Of Release=" + yearOfRelease +
                ", Main Star='" + mainStar + '\'' +
                ", Director='" + director + '\'' +
                ", Genre='" + genre + '\'' +
                ", Id='" + getId() + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getMovieNumber().equals(movie.getMovieNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieNumber());
    }

    public Movie(){
        long id;
        id = ids;
        ids++;
        setId(id);
        movieNumber = "";
        title = "";
        yearOfRelease = 0;
        mainStar = "";
        director = "";
        genre = "";
    }

    public Movie(String movieNumber, String title, int yearOfRelease ,String mainStar, String director, String genre) {
        long id;
        id = ids;
        ids++;
        setId(id);
        this.movieNumber = movieNumber;
        this.title = title;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
        this.director = director;
        this.mainStar = mainStar;
    }

    public String getMovieNumber() {
        return movieNumber;
    }

    public void setMovieNumber(String movieNumber) {
        this.movieNumber = movieNumber;
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
