package pl.jkkk.cps.logic.readerwriter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import pl.jkkk.cps.logic.exception.FileOperationException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileReaderWriter<T extends Serializable> {

    /*------------------------ FIELDS REGION ------------------------*/
    private String filename;

    /*------------------------ METHODS REGION ------------------------*/
    public FileReaderWriter(String filename) {
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

    public void writeFxChart(Image image) throws FileOperationException {
        File file = new File(filename);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }
}
    