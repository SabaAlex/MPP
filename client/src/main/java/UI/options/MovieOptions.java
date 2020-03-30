package UI.options;

/**
 * Enum with all the possible options that the Movie Service can have
 */
public enum MovieOptions {
    ADD("ADD Movie"),
    PRINT("PRINT all Movies"),
    SORT("PRINT all Movies sorted by whatever you pick"),
    FILTER("FILTER Movies"),
    UPDATE("UPDATE Movie"),
    DELETE("DELETE Movie"),
    STAT("Print the most rich years in movies");

    private String cmdMessage;

    public String getCmdMessage() {
        return cmdMessage;
    }

    MovieOptions(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}
