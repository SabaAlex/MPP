package core.model.exceptions;

/**
 * Exception used when the data provided by the user cannot be transformed in the correct type of data for storage
 */
public class DataTypeException extends MyException {
    public DataTypeException(){
        super("The type of the Data is wrong!");
    }
}
