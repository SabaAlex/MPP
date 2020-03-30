package UI.options;

/**
 * Enum with all the possible options that the Client Service can have
 */
public enum ClientOptions {
    ADD("ADD Client"),
    PRINT("PRINT all Clients"),
    SORT("PRINT all Clients sorted ascending by first name,then last name then descending by age"),
    FILTER("FILTER Clients"),
    UPDATE("UPDATE Client"),
    DELETE("DELETE Client"),
    STAT("Print the top 5 oldest clients");

    private final String cmdMessage;

    public String getCmdMessage() {
        return cmdMessage;
    }



    ClientOptions(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}
