package UI;

import Service.ClientService;
import Service.MovieService;
import UI.options.ClientOptions;
import UI.options.MovieOptions;
import UI.utils.Commands;
import model.domain.Client;
import model.domain.Movie;
import model.exceptions.DataTypeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Console {

    private ClientService clientService;
    private MovieService movieService;
    private Map<String, Runnable> fctLinks;
    private Commands commands;

    public Console(ClientService clientService, MovieService movieService) {
        this.clientService = clientService;
        this.movieService = movieService;
        commands = new Commands();
        fctLinks = new HashMap<>();
        initFunctionLinks();
    }

    private void printMenu() {
        for (Map.Entry<Integer, String> com : commands.getCommands().entrySet()) {
            String line = String.format("%4s. %s", com.getKey(), com.getValue());
            System.out.println(line);
        }

    }

    private void uiFilterClientsByName() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Client Name: ");
        String name = scanner.nextLine();

        clientService.filterClientsByName(name).forEach(System.out::println);
    }

    private void uiDeleteClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Client Id: ");
        String input = scanner.nextLine();

        long id;
        try {
            id = Long.parseLong(input);
        } catch (NumberFormatException E) {
            throw new DataTypeException();
        }

        clientService.deleteClient(id);
    }

    private void uiUpdateClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Old Client Id: ");
        String input = scanner.nextLine();

        System.out.println("Input New Client Number: ");
        String clientNumber = scanner.nextLine();

        System.out.println("Input New Client First Name: ");
        String fName = scanner.nextLine();

        System.out.println("Input New Client Last Name: ");
        String lName = scanner.nextLine();

        System.out.println("Input New Client Age: ");
        String ageStr = scanner.nextLine();

        int age;
        long id;

        try {
            age = Integer.parseInt(ageStr);
            id = Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Client client = new Client(clientNumber, fName, lName, age);
        client.setId(id);

        clientService.updateClient(client);
    }

    private void initFunctionLinks() {
        fctLinks.put("Help", this::printMenu);
        fctLinks.put(ClientOptions.ADD.getCmdMessage(), this::uiAddClient);
        fctLinks.put(ClientOptions.PRINT.getCmdMessage(), this::uiPrintAllClients);
        fctLinks.put(ClientOptions.FILTER.getCmdMessage(), this::uiFilterClientsByName);
        fctLinks.put(ClientOptions.DELETE.getCmdMessage(), this::uiDeleteClient);
        fctLinks.put(ClientOptions.UPDATE.getCmdMessage(), this::uiUpdateClient);
        fctLinks.put(MovieOptions.ADD.getCmdMessage(), this::uiAddMovie);
        fctLinks.put(MovieOptions.PRINT.getCmdMessage(), this::uiPrintAllMovie);
        fctLinks.put(MovieOptions.FILTER.getCmdMessage(), this::uiFilterMovieByTitle);
        fctLinks.put(MovieOptions.DELETE.getCmdMessage(), this::uiDeleteMovie);
        fctLinks.put(MovieOptions.UPDATE.getCmdMessage(), this::uiUpdateMovie);
    }

    private void uiUpdateMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Old Movie Id: ");
        String input = scanner.nextLine();

        System.out.println("Input Movie Number: ");
        String movieNumber = scanner.nextLine();

        System.out.println("Input Movie Title: ");
        String title = scanner.nextLine();

        System.out.println("Input Movie Year: ");
        String yearStr = scanner.nextLine();

        System.out.println("Input Movie Main Star: ");
        String mainStar = scanner.nextLine();

        System.out.println("Input Movie Director: ");
        String director = scanner.nextLine();

        System.out.println("Input Movie Genre: ");
        String genre = scanner.nextLine();

        int year;
        long id;

        try {
            year = Integer.parseInt(yearStr);
            id = Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Movie movie = new Movie(movieNumber, title, year, mainStar, director, genre);
        movie.setId(id);

        movieService.updateMovie(movie);
    }

    private void uiAddMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Movie Number: ");
        String movieNumber = scanner.nextLine();

        System.out.println("Input Movie Title: ");
        String title = scanner.nextLine();

        System.out.println("Input Movie Year: ");
        String yearStr = scanner.nextLine();

        System.out.println("Input Movie Main Star: ");
        String mainStar = scanner.nextLine();

        System.out.println("Input Movie Director: ");
        String director = scanner.nextLine();

        System.out.println("Input Movie Genre: ");
        String genre = scanner.nextLine();

        int year;

        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Movie movie = new Movie(movieNumber, title, year, mainStar, director, genre);

        movieService.addMovie(movie);
    }

    private void uiFilterMovieByTitle() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Movie Title: ");
        String name = scanner.nextLine();

        movieService.filterMoviesByTitle(name).forEach(System.out::println);
    }

    private void uiPrintAllMovie() {
        movieService.getAllMovies().forEach(System.out::println);
    }

    private void uiDeleteMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Movie Id: ");
        String input = scanner.nextLine();

        long id;
        try {
            id = Long.parseLong(input);
        } catch (NumberFormatException E) {
            throw new DataTypeException();
        }

        movieService.deleteMovie(id);
    }


    private void uiPrintAllClients() {
        clientService.getAllClients().forEach(System.out::println);
    }

    private void uiAddClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Client Number: ");
        String clientNumber = scanner.nextLine();

        System.out.println("Input Client First Name: ");
        String fName = scanner.nextLine();

        System.out.println("Input Client Last Name: ");
        String lName = scanner.nextLine();

        System.out.println("Input Client Age: ");
        String ageStr = scanner.nextLine();

        int age;

        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Client client = new Client(clientNumber, fName, lName, age);

        clientService.addClient(client);

    }

    public void run() {

        Scanner scanner = new Scanner(System.in);
        printMenu();

        while (true) {

            System.out.println("Enter input");
            String input = scanner.nextLine();
            int key;

            try {
                key = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Command needs to be a number");
                continue;
            }

            if (!commands.containsCommand(key)) {
                System.out.println("Invalid Option");
            }

            String cmd = commands.getCommandValue(key);

            if (!fctLinks.containsKey(cmd)) {
                System.out.println("Functionality not yet implemented!");
                continue;
            }

            if (cmd.equals("Exit"))
                return;

            try {
                fctLinks.get(cmd).run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}


