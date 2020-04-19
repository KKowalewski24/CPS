package pl.jkkk.cps.view.controller.animationpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import pl.jkkk.cps.view.exception.AnimationNotStartedException;
import pl.jkkk.cps.view.fxml.PopOutWindow;
import pl.jkkk.cps.view.fxml.StageController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static pl.jkkk.cps.view.constant.Constants.PATH_ANIMATION_PANEL;
import static pl.jkkk.cps.view.constant.Constants.PATH_CSS_STYLING;
import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_ANIMATION_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;
import static pl.jkkk.cps.view.fxml.FxHelper.changeTheme;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareLineChart;

public class AnimationPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    @FXML
    private LineChart lineChartSignalX;
    @FXML
    private LineChart lineChartSignalY;
    @FXML
    private LineChart lineChartCorrelation;

    private AnimationThread animationThread = new AnimationThread();

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareLineChart(lineChartSignalX);
        prepareLineChart(lineChartSignalY);
        prepareLineChart(lineChartCorrelation);
    }

    @FXML
    private void onActionButtonStartAnimation(ActionEvent actionEvent) {
        //        animationThread.startAnimation(lineChartSignalX, lineChartSignalY,
        //        lineChartCorrelation);
        XYChart.Series series = new XYChart.Series();
        //        TODO remove this
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        lineChartSignalX.getData().addAll(series);
        //        fillLineChart();
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

    @FXML
    private void onActionButtonChangeTheme(ActionEvent actionEvent) {
        changeTheme(PATH_ANIMATION_PANEL, TITLE_ANIMATION_PANEL, PATH_CSS_STYLING);
    }

    @FXML
    private void onActionButtonReloadStage(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_ANIMATION_PANEL, TITLE_ANIMATION_PANEL);
    }
}
    