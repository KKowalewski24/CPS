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
import java.util.List;

public class ReportWriter {

    /*------------------------ FIELDS REGION ------------------------*/
    public static final String PNG = ".png";
    public static final String TXT = ".txt";

    /*------------------------ METHODS REGION ------------------------*/
    public String generateFilename(String name, List<String> callArgs, String fileExtension) {
        StringBuilder args = new StringBuilder();
        callArgs.forEach((it) -> args.append(it).append("_"));

        return String.format("%s_%s%02d%02d%02d%s",
                name,
                args.toString(),
                LocalTime.now().getHour(),
                LocalTime.now().getMinute(),
                LocalTime.now().getSecond(),
                fileExtension);
    }

    public void writeFxChart(String name, List<String> mainArgs, Node object)
            throws FileOperationException {
        File file = new File(generateFilename(name, mainArgs, PNG));
        WritableImage image = object.snapshot(new SnapshotParameters(), null);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
    }

    public void writePlainText(String filename, List<String> mainArgs, String value) {
        try (FileWriter fileWriter = new FileWriter(generateFilename(filename, mainArgs, TXT))) {
            fileWriter.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
