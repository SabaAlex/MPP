package model.domain.utils;

import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class FactorySerializeCollection {
    public static String toStringClients(Collection<Client> clients){
        if (clients.size() == 0)
            return "";
        return clients.stream()
                .map(FactorySerializable::toStringEntity)
                .collect(Collectors.joining("_"));
    }

    public static String toStringMovies(Collection<Movie> movies){
        if (movies.size() == 0)
            return "";
        return movies.stream()
                .map(FactorySerializable::toStringEntity)
                .collect(Collectors.joining("_"));
    }

    public static String toStringRentals(Collection<Rental> rentals){
        if (rentals.size() == 0)
            return "";
        return rentals.stream()
                .map(FactorySerializable::toStringEntity)
                .collect(Collectors.joining("_"));
    }

    public static Collection<Client> createClients(String message){
        String[] tokens = message.split("_");
        if (message.isEmpty())
            return new ArrayList<>();
        else {
            Collection<Client> result = new ArrayList<>();
            for (String token : tokens) result.add(FactorySerializable.createClient(token));
            return result;
        }
    }

    public static Collection<Movie> createMovies(String message){
        String[] tokens = message.split("_");
        if (message.isEmpty())
            return new ArrayList<>();
        else {
            Collection<Movie> result = new ArrayList<>();
            for (String token : tokens) result.add(FactorySerializable.createMovie(token));
            return result;
        }
    }

    public static Collection<Rental> createRentals(String message){
        String[] tokens = message.split("_");
        if (message.isEmpty())
            return new ArrayList<>();
        else {
            Collection<Rental> result = new ArrayList<>();
            for (String token : tokens) result.add(FactorySerializable.createRental(token));
            return result;
        }
    }
}
