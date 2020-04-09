package pl.jkkk.cps;

import static pl.jkkk.cps.view.constant.Constants.PATH_CSS_STYLING;
import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.jkkk.cps.textinput.MainText;
import pl.jkkk.cps.view.fxml.StageController;
import pl.jkkk.cps.view.fxml.core.WindowDimensions;

public class Main {

    public static class MyApplication extends Application {
        @Override
        public void start(final Stage stage) throws Exception {
            StageController.buildStage(stage, PATH_MAIN_PANEL, TITLE_MAIN_PANEL, new WindowDimensions(1300, 700),
                    PATH_CSS_STYLING);
            StageController.getApplicationStage().setResizable(false);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            MyApplication.launch(MyApplication.class);
        } else {
            try {
                new MainText().main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

