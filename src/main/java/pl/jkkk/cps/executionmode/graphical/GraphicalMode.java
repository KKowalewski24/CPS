package pl.jkkk.cps.executionmode.graphical;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.jkkk.cps.view.fxml.StageController;
import pl.jkkk.cps.view.fxml.core.WindowDimensions;

import static pl.jkkk.cps.view.constant.Constants.PANEL_HEIGHT;
import static pl.jkkk.cps.view.constant.Constants.PANEL_WIDTH;
import static pl.jkkk.cps.view.constant.Constants.PATH_CSS_DARK_STYLING;
import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;

public class GraphicalMode extends Application {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void start(final Stage stage) throws Exception {
        StageController.buildStage(stage, PATH_MAIN_PANEL, TITLE_MAIN_PANEL,
                new WindowDimensions(PANEL_WIDTH, PANEL_HEIGHT), PATH_CSS_DARK_STYLING);
        StageController.getApplicationStage().setResizable(false);
    }

    public void main() {
        launch();
    }
}
    