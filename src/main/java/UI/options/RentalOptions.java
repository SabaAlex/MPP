package UI.options;

/**
 * Enum with all the possible options that the Movie Service can have
 */
public enum RentalOptions {
    ADD("ADD Rental"),
    PRINT("PRINT all Rentals"),
    FILTER("FILTER Rentals"),
    UPDATE("UPDATE Rentals"),
    DELETE("DELETE Rentals");

    private String cmdMessage;

    public String getCmdMessage() {
        return cmdMessage;
    }

    RentalOptions(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}