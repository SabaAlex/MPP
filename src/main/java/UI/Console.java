package UI;

import Service.ClientService;
import Service.MovieService;
import UI.options.ClientOptions;
import UI.options.MovieOptions;
import UI.utils.Commands;
import model.domain.Client;
import model.exceptions.DataTypeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Console {

    private ClientService clientService;
    private MovieService movieService;
    private Map<String, Runnable> fctLinks;
    private Commands commands;

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

        System.out.println("Input Id: ");
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

    }

    private void initFunctionLinks() {
        fctLinks.put("Print Menu", this::printMenu);
        fctLinks.put(ClientOptions.ADD.getCmdMessage(), this::uiAddClient);
        fctLinks.put(ClientOptions.PRINT.getCmdMessage(), this::uiPrintAllClients);
        fctLinks.put(ClientOptions.FILTER.getCmdMessage(), this::uiFilterClientsByName);
        fctLinks.put(ClientOptions.DELETE.getCmdMessage(), this::uiDeleteClient);
        fctLinks.put(ClientOptions.UPDATE.getCmdMessage(), this::uiUpdateClient);
    }

    public Console(ClientService clientService, MovieService movieService) {
        this.clientService = clientService;
        this.movieService = movieService;
        commands = new Commands();
        fctLinks = new HashMap<>();
        initFunctionLinks();
    }


    private void uiPrintAllClients() {
        clientService.getAllClients().forEach(System.out::println);
    }

    private void uiAddClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Client Number: ");
        String clientNumber = scanner.nextLine();

        System.out.println("Input First Name: ");
        String fName = scanner.nextLine();

        System.out.println("Input Last Name: ");
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

            try {
                fctLinks.get(cmd).run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}


