package pl.jkkk.cps.view.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlStageSetup {

    /*------------------------ FIELDS REGION ------------------------*/
    private static Stage applicationStage;
    private static PopOutWindow popOutWindow = new PopOutWindow();

    /*------------------------ METHODS REGION ------------------------*/
    private FxmlStageSetup() {
    }

    public static Stage getApplicationStage() {
        return applicationStage;
    }

    public static void setApplicationStage(Stage applicationStage) {
        FxmlStageSetup.applicationStage = applicationStage;
    }

    private static Parent loadFxml(String fxml) throws IOException {
        return new FXMLLoader(FxmlStageSetup.class.getResource(fxml)).load();
    }

    public static void buildStage(String filePath) throws IOException {
        applicationStage.setScene(new Scene(loadFxml(filePath)));
        applicationStage.sizeToScene();
        applicationStage.show();
    }

    public static void buildStage(Stage stage, String filePath, String title) throws IOException {
        setApplicationStage(stage);
        stage.setScene(new Scene(loadFxml(filePath)));
        stage.setTitle(title);
        stage.sizeToScene();
        stage.show();
    }

    public static void closeStageByButton(Button button) {
        Stage currentStage = (Stage) button.getScene().getWindow();
        currentStage.close();
    }

    public static void reloadStage(Button button, String fxmlPath,
                                   String fxmlStylePath, String title) {
        closeStageByButton(button);
        Stage mainStage = loadFxmlStage(fxmlPath, fxmlStylePath, title);
        applicationStage.close();
        applicationStage = mainStage;
    }

    //TODO
    public static Stage loadFxmlStage(String fxmlPath, String fxmlStylePath, String title) {
        try {
            Scene scene = new Scene(FXMLLoader.load(FxmlStageSetup.class.getResource(fxmlPath)));
            scene.getStylesheets().add(FxmlStageSetup.class.getResource(fxmlStylePath).toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();

            return stage;
        } catch (IOException e) {
            popOutWindow.messageBox("Stage Loading Error",
                    "Cannot Properly Load Main Stage", Alert.AlertType.ERROR);
        }

        return null;
    }
}
    