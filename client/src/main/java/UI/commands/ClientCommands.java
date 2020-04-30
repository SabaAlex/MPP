package UI.commands;

public enum ClientCommands {
    URL("http://localhost:8080/api/clients");

    private final String cmdMessage;

    public String getString() {
        return cmdMessage;
    }

    ClientCommands(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}
