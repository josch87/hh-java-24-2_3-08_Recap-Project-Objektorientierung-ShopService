package exceptions;

import java.util.NoSuchElementException;

public class NoSuchOrderException extends NoSuchElementException {
    public NoSuchOrderException() {
        super();
    }

    public NoSuchOrderException(String message) {
        super(message);
    }

}
