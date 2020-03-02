package UI;

import Service.ClientService;
import Service.MovieService;
import UI.options.ClientOptions;
import UI.options.MovieOptions;
import model.domain.Client;
import model.exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class Console {

    private ClientService clientService;
    private MovieService movieService;
    private Map<Integer, String> commands;
    private Map<String, Runnable> fctLinks;

    private void printMenu(){
        for(Map.Entry<Integer, String> com : commands.entrySet()) {
            String line = String.format("%4s. %s", com.getKey(), com.getValue());
            System.out.println(line);
        }

    }

    private void initCommands(){
        commands.put(0, "Exit");
        commands.put(commands.size(), "Print Menu");
        for (ClientOptions clientOptions : ClientOptions.values())
            commands.put(commands.size(), clientOptions.getCmdMessage());
        for (MovieOptions movieOptions : MovieOptions.values())
            commands.put(commands.size(), movieOptions.getCmdMessage());
    }

    private void initFunctionLinks(){
        fctLinks.put("Print Menu", this::printMenu);
        fctLinks.put(ClientOptions.ADD.getCmdMessage(), this::uiAddClient);
        fctLinks.put(ClientOptions.PRINT.getCmdMessage(), this::uiPrintAllClients);
    }

    public Console(ClientService clientService, MovieService movieService) {
        this.clientService = clientService;
        this.movieService = movieService;
        commands = new HashMap<>();
        fctLinks = new HashMap<>();
        initCommands();
        initFunctionLinks();
    }


    private void uiPrintAllClients() {
        clientService.getAllClients().forEach(System.out::println);
    }

    private void uiAddClient(){
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
        }
        catch (NumberFormatException e){
            throw new MyException("Age is not a number");
        }

        Client client = new Client(clientNumber, fName, lName, age);

        clientService.addClient(client);

    }

    public void run(){

        Scanner scanner = new Scanner(System.in);
        printMenu();

        while(true) {

            System.out.println("Enter input");
            String input = scanner.nextLine();
            int key;

            try {
                key = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Command needs to be a number");
                continue;
            }

            if (!commands.containsKey(key)) {
                System.out.println("Invalid Option");
            }

            String cmd = commands.get(key);

            try {
                fctLinks.get(cmd).run();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
