package pl.jkkk.cps.logic.reader;

import pl.jkkk.cps.logic.exception.FileOperationException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileReader<T> {

    /*------------------------ FIELDS REGION ------------------------*/
    private String filename;

    /*------------------------ METHODS REGION ------------------------*/
    public FileReader(String filename) {
        this.filename = filename;
    }

    public T read() throws FileOperationException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            return (T) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileOperationException(e);
        }
    }

    public void write(T object) throws FileOperationException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))) {
            output.writeObject(object);
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }
}
    