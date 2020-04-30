package UI.commands;

public enum MovieCommands {
    URL("http://localhost:8080/api/movies");

    private final String cmdMessage;

    public String getString() {
        return cmdMessage;
    }

    MovieCommands(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}
