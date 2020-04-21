package pl.jkkk.cps.view.exception;

public class AnimationNotStartedException extends RuntimeException {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public AnimationNotStartedException() {
    }

    public AnimationNotStartedException(String message) {
        super(message);
    }

    public AnimationNotStartedException(Throwable cause) {
        super(cause);
    }
}
    