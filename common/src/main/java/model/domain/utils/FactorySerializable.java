package model.domain.utils;

import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;

public  class FactorySerializable {
    public static String toStringEntity(Client client){
        if (client == null)
            return "";
        return client.getId() + "," + client.getFirstName() + "," + client.getLastName() + "," + client.getAge();
    }

    public static String toStringEntity(Movie movie){
        if (movie == null)
            return "";
        return movie.getId() + "," + movie.getTitle() + "," + movie.getYearOfRelease() +","+ movie.getMainStar() + "," + movie.getDirector() + "," + movie.getGenre();
    }

    public static String toStringEntity(Rental rental){
        if (rental == null)
            return "";
        return rental.getId() + "," + rental.getClientID() + "," + rental.getMovieID() + "," + rental.getYear() + "," + rental.getMonth() + "," + rental.getDay();
    }

    public static Client createClient(String message){
        String[] tokens = message.split(",");
        if (tokens.length == 0)
            return null;
        else
            return new Client(Long.parseLong(tokens[0]), tokens[1], tokens[2], Integer.parseInt(tokens[3]));
    }

    public static Movie createMovie(String message){
        String[] tokens = message.split(",");
        if (tokens.length == 0)
            return null;
        else
            return new Movie(Long.parseLong(tokens[0]), tokens[1], Integer.parseInt(tokens[2]), tokens[3], tokens[4], tokens[5]);
    }

    public static Rental createRental(String message){
        String[] tokens = message.split(",");
        if (tokens.length == 0)
            return null;
        else
            return new Rental(Long.parseLong(tokens[0]), Long.parseLong(tokens[1]), Long.parseLong(tokens[2]),
                Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
    }
}
