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
import repository.Sort;
import services.IService;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Console {

    private IService<Long, Client> clientService;
    private IService<Long, Movie> movieService;
    private RentalService rentalService;
    private Map<String, Runnable> fctLinks;
    private Commands commands;
    private ExecutorService execute;

    public Console(IService<Long, Client> clientService, IService<Long, Movie> movieService, RentalService rentalService, ExecutorService executor) {
        this.clientService = clientService;
        this.movieService = movieService;
        this.rentalService = rentalService;
        this.execute=executor;
        commands = new Commands();
        fctLinks = new HashMap<>();
        initFunctionLinks();
    }
    private void initFunctionLinks() {
        fctLinks.put(ClientOptions.ADD.getCmdMessage(), this::uiAddClient);
        fctLinks.put(ClientOptions.PRINT.getCmdMessage(), this::uiPrintAllClients);
        fctLinks.put(ClientOptions.SORT.getCmdMessage(), this::uiPrintAllClientsSorted);
        fctLinks.put(ClientOptions.FILTER.getCmdMessage(), this::uiFilterClientsByName);
        fctLinks.put(ClientOptions.DELETE.getCmdMessage(), this::uiDeleteClient);
        fctLinks.put(ClientOptions.UPDATE.getCmdMessage(), this::uiUpdateClient);
        fctLinks.put(ClientOptions.STAT.getCmdMessage(), this::uiStatOldestClients);
        fctLinks.put(MovieOptions.ADD.getCmdMessage(), this::uiAddMovie);
        fctLinks.put(MovieOptions.PRINT.getCmdMessage(), this::uiPrintAllMovie);
        fctLinks.put(MovieOptions.SORT.getCmdMessage(), this::uiPrintAllMoviesSorted);
        fctLinks.put(MovieOptions.FILTER.getCmdMessage(), this::uiFilterMovieByTitle);
        fctLinks.put(MovieOptions.DELETE.getCmdMessage(), this::uiDeleteMovie);
        fctLinks.put(MovieOptions.UPDATE.getCmdMessage(), this::uiUpdateMovie);
        fctLinks.put(MovieOptions.STAT.getCmdMessage(), this::uiStatMostMoviesReleasedInYear);
        fctLinks.put(RentalOptions.ADD.getCmdMessage(), this::uiAddRental);
        fctLinks.put(RentalOptions.PRINT.getCmdMessage(), this::uiPrintAllRentals);
        fctLinks.put(RentalOptions.SORT.getCmdMessage(), this::uiPrintAllRentalsSorted);
        fctLinks.put(RentalOptions.FILTER.getCmdMessage(), this::uiFilterRentalsByYear);
        fctLinks.put(RentalOptions.DELETE.getCmdMessage(), this::uiDeleteRental);
        fctLinks.put(RentalOptions.UPDATE.getCmdMessage(), this::uiUpdateRental);
        fctLinks.put(RentalOptions.STAT.getCmdMessage(), this::uiStatMonthsOfMostRentedMovie);
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
            clientService.filterEntitiesField(name).thenAcceptAsync(entity->entity.forEach(System.out::println));;
        } catch (MyException e) {
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
            clientService.deleteEntity(id);
            rentalService.DeleteClientRentals(id);

        } catch (MyException e) {
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
            clientService.updateEntity(client);
        } catch (MyException e) {
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
            rentalService.deleteEntity(id);

        } catch (MyException e) {
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
            rentalService.filterEntitiesField(Integer.toString(year)).thenAcceptAsync(entity->entity.forEach(System.out::println));;
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void uiUpdateRental() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Rental ID:");
        String rentalID = scanner.nextLine();

        System.out.println("Input new Rental Year: ");
        String yearString = scanner.nextLine();

        System.out.println("Input new Rental Month: ");
        String monthString = scanner.nextLine();

        System.out.println("Input new Rental Day: ");
        String dayString = scanner.nextLine();

        int day;
        int month;
        int year;
        long movieId = 0L;
        long clientId = 0L;
        long id;
        try {
            day = Integer.parseInt(dayString);
            month = Integer.parseInt(monthString);
            year = Integer.parseInt(yearString);
            id = Long.parseLong(rentalID);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Rental rental = new Rental(id, clientId, movieId, year, month, day);
        try {
            rentalService.updateEntity(rental);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void uiAddRental() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Rental ID:");
        String rentalID = scanner.nextLine();
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
            clientId = Long.parseLong(clientID);
            movieId = Long.parseLong(MovieID);
            id = Long.parseLong(rentalID);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Rental rental = new Rental(id, clientId, movieId, year, month, day);
        try {
            rentalService.addEntity(rental);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void uiPrintAllRentals() {
        rentalService.getAllEntities().thenAcceptAsync(entity->entity.forEach(System.out::println));
    }



    private void uiStatOldestClients() {
        System.out.println("Top 5 oldest Clients: ");
        clientService.statEntities().thenAcceptAsync(list->{
        IntStream.range(0, 5)
                .mapToObj(list::get)
                .forEach(client -> System.out.println("Age: " + client.getAge() +
                        "\nName: " + client.getFirstName() + " " + client.getLastName() + "\n"
                ));});
    }

    private void uiStatMostMoviesReleasedInYear() {
        movieService.statEntities().thenAcceptAsync(results-> {
           System.out.println("The most rich year in movies is: " + results.get(0).getYearOfRelease() + "\n");
           results.forEach(System.out::println);
       });
    }

    private void uiStatMonthsOfMostRentedMovie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Release Year: ");
        String yearString = scanner.nextLine();
        System.out.println("Input Client Least Age: ");
        String ageString = scanner.nextLine();
        int age;
        int release_year;
        try {
            release_year = Integer.parseInt(yearString);
            age = Integer.parseInt(ageString);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }
        rentalService.statEntities(Integer.toString(release_year), Integer.toString(age)).thenAcceptAsync(rentals-> {

            System.out.println("Most rented Movie of the year " + yearString + " " + movieService.FindOne(rentals.iterator().next().getMovieID()).get().getTitle());
            System.out.println("The rental months of the most rented movie by clients older than:" + ageString + " years");

            rentals.stream()
                    .map(Rental::getMonth)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .sorted(((o1, o2) -> -1 * o1.getValue().compareTo(o2.getValue())))
                    .forEach(entity -> System.out.println(entity.getKey()));
        });
        ;

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
            movieService.updateEntity(movie);
        } catch (MyException e) {
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
            id = Long.parseLong(movieID);
        } catch (NumberFormatException e) {
            throw new DataTypeException();
        }

        Movie movie = new Movie(id, title, year, mainStar, director, genre);
        try {
            movieService.addEntity(movie);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void uiFilterMovieByTitle() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Movie Title: ");
        String name = scanner.nextLine();
        try {
            movieService.filterEntitiesField(name).thenAcceptAsync(entity->entity.forEach(System.out::println));;

        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void uiPrintAllMovie() {
        movieService.getAllEntities().thenAcceptAsync(entity->entity.forEach(System.out::println));;

    }

    private void uiPrintAllClientsSorted() {
        clientService.getAllEntitiesSorted().thenAcceptAsync(entity->entity.forEach(System.out::println));

    }


    private void uiPrintAllMoviesSorted() {
        movieService.getAllEntitiesSorted().thenAcceptAsync(entity->entity.forEach(System.out::println));;
        //      Cata munca degeaba
//        List<String> criterias = Arrays.asList("Id", "MainStar", "Title", "Genre", "YearOfRelease", "Director");
//        Scanner scanner = new Scanner(System.in);
//        Sort sort = null;
//        try {
//            while (true) {
//                System.out.println("Pick order DESC or ASC: ");
//                String order = scanner.nextLine();
//                if (order.equals("done")) break;
//                Sort.Direction sortingDirection;
//                if (order.equals("ASC")) {
//                    sortingDirection = Sort.Direction.ASC;
//                } else if (order.equals("DESC")) {
//                    sortingDirection = Sort.Direction.DESC;
//                } else {
//                    System.out.println("Wrong input!");
//                    break;
//                }
//                System.out.println("Pick column be careful it should be one of(Id,MainStar,Title,Director,Genre,YearOfRelease):");
//                String columnName = scanner.nextLine();
//                if (criterias.stream().filter(criteria -> criteria == columnName).collect(Collectors.toList()).size() == 0) {
//                    System.out.println("Wrong column next time be more careful !");
//                    break;
//                }
//                if (sort == null) {
//                    sort = new Sort(sortingDirection, columnName);
//                    sort.setClassName("Movie");
//                } else {
//                    sort = sort.and(new Sort(sortingDirection, columnName));
//                }
//
//            }
//            movieService.getAllEntitiesSorted(sort).forEach(System.out::println);
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
    }
        private void uiPrintAllRentalsSorted () {
            rentalService.getAllEntitiesSorted().thenAcceptAsync(entity->entity.forEach(System.out::println));
        }

        private void uiDeleteMovie () {
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
                movieService.deleteEntity(id);
                rentalService.DeleteMovieRentals(id);

            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }


        private void uiPrintAllClients () {
        try {
            clientService.getAllEntities().thenAcceptAsync(entitry->entitry.forEach(System.out::println));
        } catch (MyException e ) {
            e.printStackTrace();
        }


        }

        private void uiAddClient () {
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
                id = Long.parseLong(clientID);
            } catch (NumberFormatException e) {
                throw new DataTypeException();
            }

            Client client = new Client(id, fName, lName, age);
            try {
                    clientService.addEntity(client);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }

        }

        public void run () {

            Scanner scanner = new Scanner(System.in);

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

        private void uiSaveToFile () {
            clientService.saveToFile();
            movieService.saveToFile();
            rentalService.saveToFile();
        }
    }


