package UI.options;

public enum ClientOptions {
    ADD("ADD Client"),
    PRINT("PRINT all Clients"),
    FILTER("FILTER Clients"),
    UPDATE("UPDATE Client"),
    DELETE("DELETE Client");

    private final String cmdMessage;

    public String getCmdMessage() {
        return cmdMessage;
    }



    ClientOptions(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}
