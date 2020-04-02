package pl.jkkk.cps.logic.readerwriter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import pl.jkkk.cps.logic.exception.FileOperationException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class ImageWriter<T extends Node> {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public String generateFilename(String type) {
        StringBuilder result = new StringBuilder();
        result.append(type)
                .append("_")
                .append(LocalTime.now().getHour())
                .append("h_")
                .append(LocalTime.now().getMinute())
                .append("min_")
                .append(LocalTime.now().getSecond())
                .append("sek")
                .append(".png");

        return result.toString();
    }

    public void writeFxChart(Class<?> type, T object) throws FileOperationException {
        File file = new File(generateFilename(type.getSimpleName()));
        WritableImage image = object.snapshot(new SnapshotParameters(), null);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }
}
    