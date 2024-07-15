package battleship.exceptions;


public class AdjacentShipException extends RuntimeException {

    public AdjacentShipException() {
    }

    public AdjacentShipException(String s) {
        super(s);
    }

    public AdjacentShipException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdjacentShipException(Throwable cause) {
        super(cause);
    }
}
