package pl.jkkk.cps.view.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlStageSetup {

    /*------------------------ FIELDS REGION ------------------------*/
    private static Stage applicationStage;

    /*------------------------ METHODS REGION ------------------------*/
    private FxmlStageSetup() {
    }

    public static Stage getApplicationStage() {
        return applicationStage;
    }

    public static void setApplicationStage(Stage applicationStage) {
        FxmlStageSetup.applicationStage = applicationStage;
    }

    /**
     * Method load fxml file.
     */
    private static Parent loadFxml(String fxml) throws IOException {
        return new FXMLLoader(FxmlStageSetup.class.getResource(fxml)).load();
    }

    /**
     * Method load stage from scratch and set `applicationStage` - use on startup of application
     * stage is passed from start method from Main class.
     */
    public static void buildStage(Stage stage, String filePath, String title) throws IOException {
        setApplicationStage(stage);
        applicationStage.setScene(new Scene(loadFxml(filePath)));
        applicationStage.setTitle(title);
        applicationStage.sizeToScene();
        applicationStage.show();
    }

    /**
     * Method load new stage and set `applicationStage` to a new one but leave the previous one
     * open.
     */
    public static void loadStage(String filePath, String title) throws IOException {
        setApplicationStage(new Stage());
        applicationStage.setScene(new Scene(loadFxml(filePath)));
        applicationStage.setTitle(title);
        applicationStage.sizeToScene();
        applicationStage.show();
    }

    /**
     * Method close previous stage and load new stage and set `applicationStage` to a new one.
     */
    public static void reloadStage(String filePath, String title) throws IOException {
        applicationStage.close();
        loadStage(filePath, title);
    }
}
    