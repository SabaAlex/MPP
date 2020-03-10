package UI;

import Service.ClientService;
import Service.MovieService;
import Service.RentalService;
import UI.options.ClientOptions;
import UI.options.MovieOptions;
import UI.options.RentalOptions;
import UI.utils.Commands;
import model.domain.Client;
import model.domain.Movie;
import model.domain.Rental;
import model.exceptions.DataTypeException;
import model.exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Console {

    private ClientService clientService;
    private MovieService movieService;
    private RentalService rentalService;
    private Map<String, Runnable> fctLinks;
    private Commands commands;

    public Console(ClientService clientService, MovieService movieService, RentalService rentalService) {
        this.clientService = clientService;
        this.movieService = movieService;
        this.rentalService=rentalService;
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
        try {
            clientService.filterClientsByName(name).forEach(System.out::println);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
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
        try {
            clientService.deleteClient(id);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void uiUpdateClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Old Client Id: ");
        String input = scanner.nextLine();


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

        Client client = new Client(id, fName, lName, age);
        try {
            clientService.updateClient(client);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void uiDeleteRental() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Rental Id: ");
        String input = scanner.nextLine();

        long id;
        try {
            id = Long.parseLong(input);
        } catch (NumberFormatException E) {
            throw new DataTypeException();
        }
        try {
            rentalService.deleteRental(id);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void uiFilterRentalsByYear() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input renting year: ");
        String yearString = scanner.nextLine();

        int year;
        try {
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException E) {
            throw new DataTypeException();
        }
        try {
            rentalService.filterRentalsByYear(year).forEach(System.out::println);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void uiUpdateRental()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Rental ID:");
        String rentalID =scanner.nextLine();
        System.out.println("Input new Client ID: ");
        String clientID = scanner.nextLine();

        System.out.println("Input new Movie ID: ");
        String MovieID = scanner.nextLine();

        System.out.println("Input new Rental Year: ");
        String yearString = scanner.nextLine();

        System.out.println("Input new Rental Month: ");
        String monthString = scanner.nextLine();

        System.out.println("Input new Rental Day: ");
        String dayString = scanner.nextLine();

        int day;
        int month;
        int year;
        long movieId;
        long clientId;
        long id;
        try {
            day = Integer.parseInt(dayString);
            month = Integer.parseInt(monthString);
            year = Integer.parseInt(yearString);
            clientId= Long.parseLong(clientID);
            movieId= Long.parseLong(MovieID);
            id= Long.parseLong(rentalID);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Rental rental = new Rental(id, clientId, movieId,year,month, day);
        try {
            rentalService.updateRental(rental);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }
    private void uiAddRental()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Rental ID:");
        String rentalID =scanner.nextLine();
        System.out.println("Input Client ID: ");
        String clientID = scanner.nextLine();

        System.out.println("Input Movie ID: ");
        String MovieID = scanner.nextLine();

        System.out.println("Input Rental Year: ");
        String yearString = scanner.nextLine();

        System.out.println("Input Rental Month: ");
        String monthString = scanner.nextLine();

        System.out.println("Input Rental Day: ");
        String dayString = scanner.nextLine();

        int day;
        int month;
        int year;
        long movieId;
        long clientId;
        long id;
        try {
            day = Integer.parseInt(dayString);
            month = Integer.parseInt(monthString);
            year = Integer.parseInt(yearString);
            clientId= Long.parseLong(clientID);
            movieId= Long.parseLong(MovieID);
            id= Long.parseLong(rentalID);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Rental rental = new Rental(id, clientId, movieId,year,month, day);
        try {
            rentalService.addRental(rental);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void uiPrintAllRentals() {
        rentalService.getAllRentals().forEach(System.out::println);
    }

    private void initFunctionLinks() {
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
        fctLinks.put(RentalOptions.ADD.getCmdMessage(), this::uiAddRental);
        fctLinks.put(RentalOptions.PRINT.getCmdMessage(), this::uiPrintAllRentals);
        fctLinks.put(MovieOptions.FILTER.getCmdMessage(), this::uiFilterRentalsByYear);
        fctLinks.put(MovieOptions.DELETE.getCmdMessage(), this::uiDeleteRental);
        fctLinks.put(MovieOptions.UPDATE.getCmdMessage(), this::uiUpdateRental);
    }

    private void uiUpdateMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Old Movie Id: ");
        String input = scanner.nextLine();

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

        Movie movie = new Movie(id, title, year, mainStar, director, genre);
        try {
            movieService.updateMovie(movie);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void uiAddMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Movie ID: ");
        String movieID = scanner.nextLine();

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
            id=Long.parseLong(movieID);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Movie movie = new Movie(id, title, year, mainStar, director, genre);
        try {
            movieService.addMovie(movie);
        }catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void uiFilterMovieByTitle() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Movie Title: ");
        String name = scanner.nextLine();
        try {
            movieService.filterMoviesByTitle(name).forEach(System.out::println);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
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
        try {
            movieService.deleteMovie(id);
        }catch( MyException e)
        {
            System.out.println(e.getMessage());
        }
    }


    private void uiPrintAllClients() {
        clientService.getAllClients().forEach(System.out::println);
    }

    private void uiAddClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Client ID: ");
        String clientID = scanner.nextLine();

        System.out.println("Input Client First Name: ");
        String fName = scanner.nextLine();

        System.out.println("Input Client Last Name: ");
        String lName = scanner.nextLine();

        System.out.println("Input Client Age: ");
        String ageStr = scanner.nextLine();

        int age;
        long id;
        try {
            age = Integer.parseInt(ageStr);
            id= Long.parseLong(clientID);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Client client = new Client(id, fName, lName, age);
        try {
            clientService.addClient(client);
        }
        catch( MyException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void run() {

        Scanner scanner = new Scanner(System.in);
        printMenu();

        while (true) {

            printMenu();

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

            if (cmd.equals("Exit")) {
                uiSaveToFile();
                return;
            }

            if (!fctLinks.containsKey(cmd)) {
                System.out.println("Functionality not yet implemented!");
                continue;
            }

            try {
                fctLinks.get(cmd).run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void uiSaveToFile() {
        clientService.saveToFile();
        movieService.saveToFile();
        rentalService.saveToFile();
    }
}


