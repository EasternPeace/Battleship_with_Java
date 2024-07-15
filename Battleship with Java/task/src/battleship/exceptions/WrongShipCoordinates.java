package battleship.exceptions;

public class WrongShipCoordinates extends RuntimeException {

    public WrongShipCoordinates() {
    }

    public WrongShipCoordinates(String s) {
        super(s);
    }

    public WrongShipCoordinates(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongShipCoordinates(Throwable cause) {
        super(cause);
    }
}
