package UI;

import UI.options.ClientOptions;
import UI.options.MovieOptions;
import UI.options.RentalOptions;
import UI.utils.Commands;
import core.model.exceptions.DataTypeException;
import core.model.exceptions.MyException;
import dto.ClientDto;
import dto.MovieDto;
import dto.RentalDto;
import dto.collections.lists.ClientListDto;
import dto.collections.lists.MovieListDto;
import dto.collections.lists.RentalListDto;
import dto.collections.sets.ClientSetDto;
import dto.collections.sets.MovieSetDto;
import dto.collections.sets.RentalSetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Console {

    private final String clientURL = "http://localhost:8080/api/clients";
    private final String movieURL = "http://localhost:8080/api/moviess";
    private final String rentalURL = "http://localhost:8080/api/rentals";

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, Runnable> fctLinks;
    private Commands commands;

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
//        CompletableFuture.supplyAsync(
//                () -> {
//                    try {
//                        return clientService.filterEntitiesField(name);
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                        return null;
//                    } }).thenAcceptAsync(entity->entity.forEach(System.out::println));
        Objects.requireNonNull(this.restTemplate.getForObject(clientURL, ClientSetDto.class, name)).getClientDtoSet().forEach(System.out::println);
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
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//
//                        clientService.deleteEntity(id);
//
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }});
        this.restTemplate.delete(clientURL + "/{id}", id);
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

        ClientDto client = new ClientDto(fName, lName, age);
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//                        clientService.updateEntity(client);
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }});
        this.restTemplate.put(clientURL, client);
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
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//                        rentalService.deleteEntity(id);
//
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }});
        this.restTemplate.delete(rentalURL + "/{id}", id);
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
//        CompletableFuture.supplyAsync(
//                () -> {
//                    try {
//                        return rentalService.filterEntitiesField(Integer.toString(year));
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                        return null;
//                    }}).thenAcceptAsync(entity->entity.forEach(System.out::println));
        Objects.requireNonNull(this.restTemplate.getForObject(rentalURL, RentalSetDto.class, year)).getRentalDtoSet().forEach(System.out::println);
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

        RentalDto rental = new RentalDto(clientId, movieId, year, month, day);
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//                        rentalService.updateEntity(rental);
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }});
        this.restTemplate.put(rentalURL, rental);
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

        RentalDto rental = new RentalDto(clientId, movieId, year, month, day);
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//                        rentalService.addEntity(rental);
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }});
        this.restTemplate.postForEntity(rentalURL, rental, RentalDto.class);
    }

    private void uiPrintAllRentals() {
//        CompletableFuture.supplyAsync(
//                () -> rentalService.getAllEntities()).thenAcceptAsync(entity->entity.forEach(System.out::println));
        Objects.requireNonNull(this.restTemplate.getForObject(rentalURL, RentalSetDto.class)).getRentalDtoSet().forEach(System.out::println);
    }



    private void uiStatOldestClients() {
        System.out.println("Top 5 oldest Clients: ");
//        CompletableFuture.supplyAsync(
//                () -> clientService.statEntities()).thenAcceptAsync(list->{
//            IntStream.range(0, 5)
//                    .mapToObj(list::get)
//                    .forEach(client -> System.out.println("Age: " + client.getAge() +
//                            "\nName: " + client.getFirstName() + " " + client.getLastName() + "\n"
//                    ));});
        Objects.requireNonNull(this.restTemplate.getForObject(clientURL, ClientListDto.class, ""))
                .getClientDtoList().stream().limit(5).forEach(clientDto -> System.out.println("Age: " + clientDto.getAge() +
                           "\nName: " + clientDto.getFirstName() + " " + clientDto.getLastName() + "\n"
                   ));
    }

    private void uiStatMostMoviesReleasedInYear() {
//        CompletableFuture.supplyAsync(
//                () -> movieService.statEntities()).thenAcceptAsync(results-> {
//            System.out.println("The most rich year in movies is: " + results.get(0).getYearOfRelease() + "\n");
//            results.forEach(System.out::println);
//        });
        List<MovieDto> results = Objects.requireNonNull(this.restTemplate.getForObject(movieURL, MovieListDto.class, ""))
                .getMovieDtoList();
        results.stream().findFirst().orElseThrow(() -> new MyException("No movies to do statistics on!"));
        System.out.println("The most rich year in movies is: " + results.stream().findFirst().get().getYearOfRelease() + "\n");
        results.forEach(System.out::println);
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
//        CompletableFuture.supplyAsync(
//                () -> rentalService.statEntities(Integer.toString(release_year), Integer.toString(age))).thenAcceptAsync(rentals-> {
//
//            System.out.println("Most rented Movie of the year " + yearString + " " + movieService.FindOne(rentals.iterator().next().getMovieID()).get().getTitle());
//            System.out.println("The rental months of the most rented movie by clients older than:" + ageString + " years");
//
//            rentals.stream()
//                    .map(Rental::getMonth)
//                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
//                    .entrySet()
//                    .stream()
//                    .sorted(((o1, o2) -> -1 * o1.getValue().compareTo(o2.getValue())))
//                    .forEach(entity -> System.out.println(entity.getKey()));
//        });
        List<RentalDto> rentals = this.restTemplate.getForObject(rentalURL, RentalListDto.class, Integer.toString(release_year), Integer.toString(age)).getRentalDtoList();
        rentals.stream().findFirst().orElseThrow(() -> new MyException("No rentals to do statistics!"));
        System.out.println("Most rented Movie of the year " + yearString + " " +
                this.restTemplate.getForObject(movieURL + "/{id}", MovieDto.class ,rentals.stream().findFirst().get().getMovieID()).getTitle());
        System.out.println("The rental months of the most rented movie by clients older than:" + ageString + " years");

        rentals.stream()
                .map(RentalDto::getMonth)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(((o1, o2) -> -1 * o1.getValue().compareTo(o2.getValue())))
                .forEach(entity -> System.out.println(entity.getKey()));
    }

    private void uiUpdateMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Old Movie Id: ");
        String input = scanner.nextLine();

        System.out.println("Input Movie Title: ");
        String title = scanner.nextLine();

        System.out.println("Input Movie Year: ");
        String yearStr = scanner.nextLine();

        System.out.println("Input Movie client.config.Main Star: ");
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

        MovieDto movie = new MovieDto(title, year, mainStar, director, genre);
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//                        movieService.updateEntity(movie);
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }});
        this.restTemplate.put(movieURL, movie);
    }

    private void uiAddMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Movie ID: ");
        String movieID = scanner.nextLine();

        System.out.println("Input Movie Title: ");
        String title = scanner.nextLine();

        System.out.println("Input Movie Year: ");
        String yearStr = scanner.nextLine();

        System.out.println("Input Movie client.config.Main Star: ");
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

        MovieDto movie = new MovieDto(title, year, mainStar, director, genre);
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//                        movieService.addEntity(movie);
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }});
        this.restTemplate.postForEntity(movieURL, movie, MovieDto.class);
    }

    private void uiFilterMovieByTitle() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Movie Title: ");
        String title = scanner.nextLine();
//        CompletableFuture.supplyAsync(
//                () -> {
//                    try {
//                        return movieService.filterEntitiesField(name);
//
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                        return null;
//                    }}).thenAcceptAsync(entity->entity.forEach(System.out::println));
        Objects.requireNonNull(this.restTemplate.getForObject(movieURL, MovieSetDto.class, title)).getMovieDtoSet().forEach(System.out::println);
    }

    private void uiPrintAllMovie() {
//        CompletableFuture.supplyAsync(
//                () -> {
//                    return movieService.getAllEntities();}).thenAcceptAsync(entity->entity.forEach(System.out::println));;
        Objects.requireNonNull(this.restTemplate.getForObject(movieURL, MovieSetDto.class)).getMovieDtoSet().forEach(System.out::println);
    }

    private void uiPrintAllClientsSorted() {
//        CompletableFuture.supplyAsync(
//                () -> {
//                    return clientService.getAllEntitiesSorted();}).thenAcceptAsync(entity->entity.forEach(System.out::println));
        Objects.requireNonNull(this.restTemplate.getForObject(clientURL, ClientListDto.class)).getClientDtoList().forEach(System.out::println);
    }


    private void uiPrintAllMoviesSorted() {
        Objects.requireNonNull(this.restTemplate.getForObject(movieURL, MovieListDto.class)).getMovieDtoList().forEach(System.out::println);
//        CompletableFuture.supplyAsync(
//                () -> movieService.getAllEntitiesSorted()).thenAcceptAsync(entity->entity.forEach(System.out::println));;
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
//        CompletableFuture.supplyAsync(
//                () ->
//                        rentalService.getAllEntitiesSorted()).thenAcceptAsync(entity->entity.forEach(System.out::println));
        Objects.requireNonNull(this.restTemplate.getForObject(rentalURL, RentalListDto.class)).getRentalDtoList().forEach(System.out::println);
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
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//                        movieService.deleteEntity(id);
//
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }});
        this.restTemplate.delete(movieURL + "/{id}", id);
    }


    private void uiPrintAllClients () {
//        CompletableFuture.supplyAsync(
//                () ->{
//                    try {
//
//                        return clientService.getAllEntities();
//                    } catch (MyException e ) {
//                        e.printStackTrace();
//                        return null;
//                    }}).thenAcceptAsync(entitry->entitry.forEach(System.out::println));
        Objects.requireNonNull(this.restTemplate.getForObject(clientURL, ClientSetDto.class)).getClientDtoSet().forEach(System.out::println);

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

        ClientDto client = new ClientDto(fName, lName, age);
//        CompletableFuture.runAsync(
//                () -> {
//                    try {
//                        clientService.addEntity(client);
//                    } catch (MyException e) {
//                        System.out.println(e.getMessage());
//                    }
//                });
        this.restTemplate.postForEntity(clientURL, client, ClientDto.class);
    }

    public void run () {
        commands = new Commands();
        fctLinks = new HashMap<>();
        initFunctionLinks();

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

}


