package pl.jkkk.cps.view.controller.animationpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import pl.jkkk.cps.view.exception.AnimationNotStartedException;
import pl.jkkk.cps.view.fxml.PopOutWindow;
import pl.jkkk.cps.view.fxml.StageController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;

public class AnimationPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    private AnimationThread animationThread = new AnimationThread();

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onActionButtonStartAnimation(ActionEvent actionEvent) {
        animationThread.startAnimation();
    }

    @FXML
    private void onActionButtonReturn(ActionEvent actionEvent) {
        if (!animationThread.getIsAnimationRunning().get()) {
            StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
        } else {
            try {
                animationThread.stopAnimation();
                TimeUnit.MILLISECONDS.sleep(200);
                StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
            } catch (AnimationNotStartedException e) {
                PopOutWindow.messageBox("Błąd Zatrzymania",
                        "Animacja nie została rozpoczęta",
                        Alert.AlertType.WARNING);
            } catch (InterruptedException e) {
                PopOutWindow.messageBox("Błąd Zatrzymania",
                        "Nie udało się poprawnie zatrzymać animacji",
                        Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    private void onActionButtonStopAnimation(ActionEvent actionEvent) {
        try {
            animationThread.stopAnimation();
        } catch (AnimationNotStartedException e) {
            PopOutWindow.messageBox("Błąd Zatrzymania",
                    "Animacja nie została rozpoczęta",
                    Alert.AlertType.WARNING);
        }
    }
}
    