package pl.jkkk.cps.view.controller.animationpanel;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pl.jkkk.cps.view.fxml.StageController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;

public class AnimationPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    private AtomicBoolean isAnimationRunning = new AtomicBoolean(false);

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onActionButtonStartAnimation(ActionEvent actionEvent) {
        isAnimationRunning.set(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int i = 0;
                while (isAnimationRunning.get()) {
                    i++;
                    System.out.println("acdedededed " + i);
                }
                System.out.println("koniec");
                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    private synchronized void onActionButtonReturn(ActionEvent actionEvent) {
        stopAnimation();
        StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
    }

    @FXML
    private void onActionButtonStopAnimation(ActionEvent actionEvent) {
        stopAnimation();
    }

    private void stopAnimation() {
        isAnimationRunning.set(false);
    }
}
    