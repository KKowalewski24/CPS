package pl.jkkk.cps.view.controller.animationpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.model.simulator.DistanceSensor;
import pl.jkkk.cps.logic.model.simulator.Environment;
import pl.jkkk.cps.logic.readerwriter.ReportWriter;
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
import static pl.jkkk.cps.view.fxml.FxHelper.getTextFieldValueToDouble;
import static pl.jkkk.cps.view.fxml.FxHelper.getTextFieldValueToInteger;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.textFieldSetEditable;
import static pl.jkkk.cps.view.fxml.FxHelper.textFieldSetValue;

public class AnimationPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    @FXML
    private HBox paneAnimationPanel;
    @FXML
    private CheckBox checkBoxAnimationPanelReport;

    @FXML
    private TextField textFieldTimeStep;
    @FXML
    private TextField textFieldSignalVelocity;
    @FXML
    private TextField textFieldItemVelocity;
    @FXML
    private TextField textFieldStartItemDistance;
    @FXML
    private TextField textFieldProbeSignalTerm;
    @FXML
    private TextField textFieldSampleRate;
    @FXML
    private TextField textFieldBufferLength;
    @FXML
    private TextField textFieldReportTerm;

    @FXML
    private TextField textFieldResultTimeStamp;
    @FXML
    private TextField textFieldResultRealDistance;
    @FXML
    private TextField textFieldResultCalculatedDistance;

    @FXML
    private LineChart lineChartSignalProbe;
    @FXML
    private NumberAxis axisXSignalProbe;
    @FXML
    private LineChart lineChartSignalFeedback;
    @FXML
    private NumberAxis axisXSignalFeedback;
    @FXML
    private LineChart lineChartSignalCorrelation;
    @FXML
    private NumberAxis axisXSignalCorrelation;

    private AnimationThread animationThread = new AnimationThread();
    private ReportWriter reportWriter = new ReportWriter();

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldSetValue(textFieldTimeStep, String.valueOf(0.1));
        textFieldSetValue(textFieldSignalVelocity, String.valueOf(100));
        textFieldSetValue(textFieldItemVelocity, String.valueOf(0.5));
        textFieldSetValue(textFieldStartItemDistance, String.valueOf(25));
        textFieldSetValue(textFieldProbeSignalTerm, String.valueOf(1));
        textFieldSetValue(textFieldSampleRate, String.valueOf(20));
        textFieldSetValue(textFieldBufferLength, String.valueOf(60));
        textFieldSetValue(textFieldReportTerm, String.valueOf(0.5));

        prepareLineChart(lineChartSignalProbe);
        prepareLineChart(lineChartSignalFeedback);
        prepareLineChart(lineChartSignalCorrelation);
    }

    @FXML
    private void onActionButtonStartAnimation(ActionEvent actionEvent) {
        changeParamsTextFieldsEditable(false);

        try {
            Double timeStep = getTextFieldValueToDouble(textFieldTimeStep);
            Double signalVelocity = getTextFieldValueToDouble(textFieldSignalVelocity);
            Double itemVelocity = getTextFieldValueToDouble(textFieldItemVelocity);
            Double startItemDistance = getTextFieldValueToDouble(textFieldStartItemDistance);
            Double probeSignalTerm = getTextFieldValueToDouble(textFieldProbeSignalTerm);
            Double sampleRate = getTextFieldValueToDouble(textFieldSampleRate);
            Integer bufferLength = getTextFieldValueToInteger(textFieldBufferLength);
            Double reportTerm = getTextFieldValueToDouble(textFieldReportTerm);

            DistanceSensor distanceSensor = new DistanceSensor(probeSignalTerm,
                    sampleRate, bufferLength, reportTerm, signalVelocity);
            Environment environment = new Environment(timeStep, signalVelocity,
                    itemVelocity, distanceSensor, startItemDistance);

            animationThread.startAnimation(environment, lineChartSignalProbe,
                    lineChartSignalFeedback, lineChartSignalCorrelation,
                    axisXSignalProbe, axisXSignalFeedback, axisXSignalCorrelation,
                    textFieldResultTimeStamp, textFieldResultRealDistance,
                    textFieldResultCalculatedDistance);
        } catch (NumberFormatException e) {
            PopOutWindow.messageBox("Błędne Dane",
                    "Wprowadzono błędne dane",
                    Alert.AlertType.WARNING);
        }
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
        changeParamsTextFieldsEditable(true);
        StageController.getApplicationStage().setResizable(false);
    }

    @FXML
    private void onActionButtonStopAnimation(ActionEvent actionEvent) {
        try {
            animationThread.stopAnimation();
            changeParamsTextFieldsEditable(true);
        } catch (AnimationNotStartedException e) {
            PopOutWindow.messageBox("Błąd Zatrzymania",
                    "Animacja nie została rozpoczęta",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onActionButtonChangeTheme(ActionEvent actionEvent) {
        changeTheme(PATH_ANIMATION_PANEL, TITLE_ANIMATION_PANEL, PATH_CSS_STYLING);
        StageController.getApplicationStage().setResizable(false);
    }

    @FXML
    private void onActionButtonReloadStage(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_ANIMATION_PANEL, TITLE_ANIMATION_PANEL);
        StageController.getApplicationStage().setResizable(false);
    }

    @FXML
    private void onActionButtonGenerateReport(ActionEvent actionEvent) {
        try {
            reportWriter.writeFxChart("Animation", Main.getMainArgs(), paneAnimationPanel);
        } catch (FileOperationException e) {
            e.printStackTrace();
        }
    }

    private void changeParamsTextFieldsEditable(boolean value) {
        textFieldSetEditable(value,
                textFieldTimeStep, textFieldSignalVelocity,
                textFieldItemVelocity, textFieldStartItemDistance,
                textFieldProbeSignalTerm, textFieldSampleRate,
                textFieldBufferLength, textFieldReportTerm);
    }
}
    