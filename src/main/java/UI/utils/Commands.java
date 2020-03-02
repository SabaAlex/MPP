package UI.utils;

import UI.options.ClientOptions;
import UI.options.MovieOptions;

import java.util.HashMap;
import java.util.Map;

public class Commands {

    private Map<Integer, String> commands;

    public Map<Integer, String> getCommands() {
        return commands;
    }

    public boolean containsCommand(int key){
        return commands.containsKey(key);
    }

   public String getCommandValue(int key){
        return commands.get(key);
   }

    public Commands() {
        this.commands = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        commands.put(0, "Exit");
        commands.put(commands.size(), "Help");
        for (ClientOptions clientOptions : ClientOptions.values())
            commands.put(commands.size(), clientOptions.getCmdMessage());
        for (MovieOptions movieOptions : MovieOptions.values())
            commands.put(commands.size(), movieOptions.getCmdMessage());
    }
}
