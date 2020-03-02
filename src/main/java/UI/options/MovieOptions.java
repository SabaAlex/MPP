package UI.options;

public enum MovieOptions {
    ADD("ADD Movie"),
    PRINT("PRINT all Movies"),
    FILTER("FILTER Movies"),
    UPDATE("UPDATE Movie"),
    DELETE("DELETE Movie");

    private String cmdMessage;

    public String getCmdMessage() {
        return cmdMessage;
    }

    MovieOptions(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}
