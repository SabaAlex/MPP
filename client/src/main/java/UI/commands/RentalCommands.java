package UI.commands;

public enum RentalCommands {
    URL("http://localhost:8080/api/rentals");

    private final String cmdMessage;

    public String getString() {
        return cmdMessage;
    }

    RentalCommands(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}
