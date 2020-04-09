package pl.jkkk.cps.executionmode.graphical;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.jkkk.cps.view.fxml.StageController;
import pl.jkkk.cps.view.fxml.core.WindowDimensions;

import static pl.jkkk.cps.view.constant.Constants.PATH_CSS_STYLING;
import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;

public class GraphicalMode extends Application {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void start(final Stage stage) throws Exception {
        StageController.buildStage(stage, PATH_MAIN_PANEL, TITLE_MAIN_PANEL,
                new WindowDimensions(1300, 700),
                PATH_CSS_STYLING);
        StageController.getApplicationStage().setResizable(false);
    }

    public void main() {
        launch();
    }
}
    