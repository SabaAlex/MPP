package UI.utils;

import UI.options.ClientOptions;
import UI.options.MovieOptions;
import UI.options.RentalOptions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Commands {

    private Map<Integer, String> commands;


    public Map<Integer, String> getCommands() {
        return commands;
    }

    /**
     * Checks if a command exist in the command map
     *
     * @param key the key of the an associated value in the commands map
     * @return true if it is in the map, false otherwise
     */
    public boolean containsCommand(int key){
        return commands.containsKey(key);
    }

    /**
     * Gets the value of a command given a key
     *
     * @param key the key of the an associated value in the commands map
     * @return the value of the associated key
     */
    public String getCommandValue(int key){
        return commands.get(key);
    }

    public Commands() {
        this.commands = new HashMap<>();
        initCommands();
    }

    /**
     * Adds to the command map all the existing commands the enums that contain commands for each service
     * ClientOptions
     * MovieOptions
     * Rental
     */
    private void initCommands() {
        commands.put(0, "Exit");
        for (ClientOptions clientOptions : ClientOptions.values())
            commands.put(commands.size(), clientOptions.getCmdMessage());
        for (MovieOptions movieOptions : MovieOptions.values())
            commands.put(commands.size(), movieOptions.getCmdMessage());
        for (RentalOptions rentalOptions : RentalOptions.values())
            commands.put(commands.size(), rentalOptions.getCmdMessage());
    }
}
