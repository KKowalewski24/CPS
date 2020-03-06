package pl.jkkk.cps;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.jkkk.cps.view.util.StageController;
import pl.jkkk.cps.view.util.core.WindowDimensions;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;

public class Main extends Application {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void start(Stage stage) {
        StageController.buildStage(stage, PATH_MAIN_PANEL,
                TITLE_MAIN_PANEL, new WindowDimensions(1200, 900));
    }

    public static void main(String[] args) {
        launch();
    }
}
