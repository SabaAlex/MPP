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

public class Console {

    private ClientService clientService;
    private MovieService movieService;
    private Map<Integer, String> commands;

    private void printMenu(){

        for(Map.Entry<Integer, String> com : commands.entrySet()) {
            String line = String.format("%4s : %s", com.getKey(), com.getValue());
            System.out.println(line);
        }

    }

    private void initCommands(){
        for (ClientOptions clientOptions : ClientOptions.values())
            commands.put(commands.size() + 1, clientOptions.getCmdMessage());
        for (MovieOptions movieOptions : MovieOptions.values())
            commands.put(commands.size() + 1, movieOptions.getCmdMessage());
    }

    public Console(ClientService clientService, MovieService movieService) {
        this.clientService = clientService;
        this.movieService = movieService;
        commands = new HashMap<>();
        initCommands();
    }

    private void uiAddClient(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Input Client Number: ");
        String clientNumber = scan.nextLine();

        System.out.println("Input First Name: ");
        String fName = scan.nextLine();

        System.out.println("Input Last Name: ");
        String lName = scan.nextLine();

        System.out.println("Input Client Age: ");
        String ageStr = scan.nextLine();

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

        while(true) {
            System.out.println("Input the option: ");
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

            /// key has the command
            String cmd = commands.get(key);


            try {
                if (cmd.equals(ClientOptions.ADD.getCmdMessage()))
                    uiAddClient();
                if (cmd.equals(ClientOptions.PRINT.getCmdMessage()))
                    uiPrintAllClients();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void uiPrintAllClients() {
        clientService.getAllClients().forEach(System.out::println);
    }
}
