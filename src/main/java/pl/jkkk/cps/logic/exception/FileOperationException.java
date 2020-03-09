package pl.jkkk.cps.logic.exception;

import java.io.IOException;

public class FileOperationException extends IOException {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public FileOperationException() {
    }

    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(Throwable cause) {
        super(cause);
    }
}
    