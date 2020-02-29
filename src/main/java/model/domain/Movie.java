package model.domain;

public class Movie {

    String title;
    String genre;
    int yearOfRelease;
    String director;
    String mainStar;

    @Override
    public String toString() {
        return "Title: '" + title + '\'' +
                ", Genre: '" + genre + '\'' +
                ", Year Of Release: " + yearOfRelease +
                ", Director: '" + director + '\'' +
                ", Main Star: '" + mainStar + '\'';
    }

    public Movie(){
        title = "";
        yearOfRelease = 0;
        mainStar = "";
        director = "";
        genre = "";
    }

    public Movie(String title, int yearOfRelease ,String mainStar, String director, String genre) {
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
