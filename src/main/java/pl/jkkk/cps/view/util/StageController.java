package pl.jkkk.cps.view.util;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import pl.jkkk.cps.view.util.core.FxmlStageSetup;
import pl.jkkk.cps.view.util.core.WindowDimensions;

import java.io.IOException;

public class StageController {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public static void buildStage(Stage stage, String filePath,
                                  String title, WindowDimensions dimensions) {
        try {
            FxmlStageSetup.buildStage(stage, filePath, title, dimensions);
        } catch (IOException | IllegalStateException e) {
            PopOutWindow.messageBox("Stage Building Error",
                    "Stage cannot be properly built", Alert.AlertType.ERROR);
        }
    }

    public static void loadStage(String filePath, String title) {
        try {
            FxmlStageSetup.loadStage(filePath, title);
        } catch (IOException | IllegalStateException e) {
            PopOutWindow.messageBox("Stage Loading Error",
                    "Stage cannot be properly loaded", Alert.AlertType.ERROR);
        }
    }

    public static void reloadStage(String filePath, String title) {
        try {
            FxmlStageSetup.reloadStage(filePath, title);
        } catch (IOException | IllegalStateException e) {
            PopOutWindow.messageBox("Stage Reloading Error",
                    "Stage cannot be properly reloaded", Alert.AlertType.ERROR);
        }
    }
}
    