package pl.jkkk.cps.logic.exception;

public class NotSameLengthException extends RuntimeException {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public NotSameLengthException() {
    }

    public NotSameLengthException(String message) {
        super(message);
    }

    public NotSameLengthException(Throwable cause) {
        super(cause);
    }
}
    