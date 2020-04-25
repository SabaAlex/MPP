package model.exceptions;

import java.io.Serializable;

/**
 * Generic exception created as a template for other, more specific exception
 */
public class MyException extends RuntimeException implements Serializable {
    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException() {

    }
}
