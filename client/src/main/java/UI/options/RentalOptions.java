package UI.options;

/**
 * Enum with all the possible options that the Movie Service can have
 */
public enum RentalOptions {
    ADD("ADD Rental"),
    PRINT("PRINT all Rentals"),
    SORT("PRINT all Rentals sorted"),
    FILTER("FILTER Rentals"),
    UPDATE("UPDATE Rentals"),
    DELETE("DELETE Rentals"),
    STAT("Print the the sorted rented months(in terms of number of rents) of the most rented movie of a given year by clients older than a given age:");

    private String cmdMessage;

    public String getCmdMessage() {
        return cmdMessage;
    }

    RentalOptions(String cmdMessage) {
        this.cmdMessage = cmdMessage;
    }
}
