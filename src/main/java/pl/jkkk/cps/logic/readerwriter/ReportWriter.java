package pl.jkkk.cps.logic.readerwriter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import pl.jkkk.cps.logic.exception.FileOperationException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class ReportWriter {

    /*------------------------ FIELDS REGION ------------------------*/
    public static final String PNG = ".png";
    public static final String TXT = ".txt";

    /*------------------------ METHODS REGION ------------------------*/
    public String generateFilename(String type, String fileExtension) {
        StringBuilder result = new StringBuilder();
        result.append(type)
                .append("_")
                .append(LocalTime.now().getHour())
                .append("h_")
                .append(LocalTime.now().getMinute())
                .append("min_")
                .append(LocalTime.now().getSecond())
                .append("sek")
                .append(fileExtension);

        return result.toString();
    }

    public void writeFxChart(Class<?> type, Node object) throws FileOperationException {
        File file = new File(generateFilename(type.getSimpleName(), PNG));
        WritableImage image = object.snapshot(new SnapshotParameters(), null);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }

    public void writePlainText(String filename, String value) {
        try (FileWriter fileWriter = new FileWriter(generateFilename(filename, TXT))) {
            fileWriter.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    