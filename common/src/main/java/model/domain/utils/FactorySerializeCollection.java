package model.domain.utils;

import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class FactorySerializeCollection {
    public static String toStringClients(Collection<Client> clients){
        return clients.stream()
                .map(FactorySerializable::toStringEntity)
                .collect(Collectors.joining("_"));
    }

    public static String toStringMovies(Collection<Movie> movies){
        return movies.stream()
                .map(FactorySerializable::toStringEntity)
                .collect(Collectors.joining("_"));
    }

    public static String toStringRentals(Collection<Rental> rentals){
        return rentals.stream()
                .map(FactorySerializable::toStringEntity)
                .collect(Collectors.joining("_"));
    }

    public static Collection<Client> createClients(String message){
        String[] tokens = message.split("_");
        Collection<Client> result = new ArrayList<>();
        for (String token : tokens) result.add(FactorySerializable.createClient(token));
        return result;
    }

    public static Collection<Movie> createMovies(String message){
        String[] tokens = message.split("_");
        Collection<Movie> result = new ArrayList<>();
        for (String token : tokens) result.add(FactorySerializable.createMovie(token));
        return result;
    }

    public static Collection<Rental> createRentals(String message){
        String[] tokens = message.split("_");
        Collection<Rental> result = new ArrayList<>();
        for (String token : tokens) result.add(FactorySerializable.createRental(token));
        return result;
    }
}
