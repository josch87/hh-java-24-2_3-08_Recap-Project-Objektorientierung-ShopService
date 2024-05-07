package exceptions;

import java.util.NoSuchElementException;

public class NoSuchProductException extends NoSuchElementException {
    public NoSuchProductException() {
        super();
    }

    public NoSuchProductException(String message) {
        super(message);
    }

}
