package model.domain;

import java.util.Objects;

public class Rental extends BaseEntity<Long> {

    private Long ClientID;
    private Long MovieID;
    private int year;
    private int day;
    private int month;

    public Rental(Long ID, Long ClientID, Long MovieID, int year, int month, int day) {
        super.setId(ID);
        this.ClientID = ClientID;
        this.MovieID = MovieID;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return super.getId().equals(rental.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

    @Override
    public String toString() {
        return "Rental ID: " + getId() +
                ", Client ID: " + ClientID +
                ", Movie ID: " + MovieID +
                ", Year: " + year +
                ", Month: " + month  +
                ", Day: " + day;
    }

    public Long getClientID() {
        return ClientID;
    }

    public void setClientID(Long clientID) {
        ClientID = clientID;
    }

    public Long getMovieID() {
        return MovieID;
    }

    public void setMovieID(Long movieID) {
        MovieID = movieID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
