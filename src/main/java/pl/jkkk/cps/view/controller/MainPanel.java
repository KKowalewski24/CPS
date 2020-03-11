package pl.jkkk.cps.view.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.OperationType;
import pl.jkkk.cps.logic.model.Series;
import pl.jkkk.cps.logic.model.SignalType;
import pl.jkkk.cps.view.helper.ChartRecord;
import pl.jkkk.cps.view.helper.CustomTab;
import pl.jkkk.cps.view.helper.CustomTabPane;
import pl.jkkk.cps.view.util.PopOutWindow;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;
import static pl.jkkk.cps.view.helper.ChartHelper.castTabPaneToCustomTabPane;
import static pl.jkkk.cps.view.helper.ChartHelper.fillBarChart;
import static pl.jkkk.cps.view.helper.ChartHelper.fillComboBox;
import static pl.jkkk.cps.view.helper.ChartHelper.fillLineChart;
import static pl.jkkk.cps.view.helper.ChartHelper.textFieldSetValue;

public class MainPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    /* LEFT SIDE */
    @FXML
    private ComboBox comboBoxSignalTypes;
    @FXML
    private TextField textFieldAmplitude;
    @FXML
    private TextField textFieldStartTime;
    @FXML
    private TextField textFieldSignalDuration;
    @FXML
    private TextField textFieldBasicPeriod;
    @FXML
    private TextField textFieldFillFactor;
    @FXML
    private TextField textFieldJumpTime;
    @FXML
    private TextField textFieldProbability;
    @FXML
    private TextField textFieldSamplingFrequency;
    @FXML
    private ComboBox comboBoxOperationTypes;

    /* RIGHT SIDE */
    @FXML
    private TabPane tabPaneResults;
    @FXML
    private Pane paramsTab;
    @FXML
    private TextField textFieldSignalAverageValue;
    @FXML
    private TextField textFieldAbsoluteSignalAverageValue;
    @FXML
    private TextField textFieldSignalEffectiveValue;
    @FXML
    private TextField textFieldSignalVariance;
    @FXML
    private TextField textFieldAverageSignalStrength;
    @FXML
    private TextField textFieldMediumSquareError;
    @FXML
    private TextField textFieldSignalNoiseRatio;
    @FXML
    private TextField textFieldPeakSignalNoiseRatio;
    @FXML
    private TextField textFieldMaximumDifference;
    @FXML
    private TextField textFieldEffectiveNumberOfBits;
    @FXML
    private TextField textFieldTransformationTime;
    @FXML
    private Spinner spinnerHistogramRange;

    /* OTHER FIELDS */
    private Series lineChartData;
    private List<ChartRecord<String, Number>> barChartData;

    /*------------------------ METHODS REGION ------------------------*/

    /*------------------------ PREPARE EMPTY PANES AND CHARTS ------------------------*/
    private void fillTextFields() {
        textFieldSetValue(textFieldAmplitude, String.valueOf(1));
        textFieldSetValue(textFieldStartTime, String.valueOf(0));
        textFieldSetValue(textFieldSignalDuration, String.valueOf(5));
        textFieldSetValue(textFieldBasicPeriod, String.valueOf(1));
        textFieldSetValue(textFieldFillFactor, String.valueOf(0.5));
        textFieldSetValue(textFieldJumpTime, String.valueOf(2));
        textFieldSetValue(textFieldProbability, String.valueOf(0.5));
        textFieldSetValue(textFieldSamplingFrequency, String.valueOf(16));
    }

    private void prepareTabPaneInputs() {
        fillComboBox(comboBoxSignalTypes, Stream.of(
                SignalType.UNIFORM_NOISE.getName(),
                SignalType.GAUSSIAN_NOISE.getName(),
                SignalType.SINUSOIDAL_SIGNAL.getName(),
                SignalType.SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL.getName(),
                SignalType.SINUSOIDAL_RECTIFIED_IN_TWO_HALVES.getName(),
                SignalType.RECTANGULAR_SIGNAL.getName(),
                SignalType.SYMMETRICAL_RECTANGULAR_SIGNAL.getName(),
                SignalType.TRIANGULAR_SIGNAL.getName(),
                SignalType.UNIT_JUMP.getName(),
                SignalType.UNIT_IMPULSE.getName(),
                SignalType.IMPULSE_NOISE.getName()
        ).collect(Collectors.toCollection(ArrayList::new)));

        fillComboBox(comboBoxOperationTypes, Stream.of(
                OperationType.ADDITION.getName(),
                OperationType.SUBTRACTION.getName(),
                OperationType.MULTIPLICATION.getName(),
                OperationType.DIVISION.getName()
        ).collect(Collectors.toCollection(ArrayList::new)));

        fillTextFields();
    }

    private void prepareTabPaneResults(int index) {
        LineChart lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setCreateSymbols(false);
        BarChart barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setAnimated(true);

        tabPaneResults.getTabs().add(new Tab("Karta " + index,
                new CustomTabPane(
                        new CustomTab("Wykres", lineChart, false),
                        new CustomTab("Histogram", barChart, false),
                        new CustomTab("Parametry", paramsTab, false)
                )));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerHistogramRange.setValueFactory(new SpinnerValueFactory
                .IntegerSpinnerValueFactory(5, 20, 10, 5));
        prepareTabPaneInputs();
        prepareTabPaneResults(0);
    }

    /*------------------------ BUTTON TOP BAR ------------------------*/
    @FXML
    private void onActionButtonReloadStage(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
    }

    @FXML
    private void onActionButtonAddNewTab(ActionEvent actionEvent) {
        ObservableList<Tab> tabList = tabPaneResults.getTabs();
        if (tabList.size() != 0) {
            prepareTabPaneResults(tabList.size());
        } else {
            PopOutWindow.messageBox("Brak Wykresów",
                    "Wykresy nie zostały jeszcze wygenerowane", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onActionButtonCloseProgram(ActionEvent actionEvent) {
        System.exit(0);
    }

    /*------------------------ FILE READ / WRITE ------------------------*/
    @FXML
    private void onActionLoadChart(ActionEvent actionEvent) {
        try {
            new FileChooser().showOpenDialog(StageController.getApplicationStage()).getName();
            //TODO ADD IMPL
        } catch (NullPointerException e) {
            PopOutWindow.messageBox("Błąd Ładowania Pliku",
                    "Nie można załadować wybranego pliku", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onActionSaveChart(ActionEvent actionEvent) {
        try {
            new FileChooser().showOpenDialog(StageController.getApplicationStage()).getName();
            //TODO ADD IMPL
        } catch (NullPointerException e) {
            PopOutWindow.messageBox("Błąd Zapisu Do Pliku",
                    "Nie można zapisać do wybranego pliku", Alert.AlertType.WARNING);
        }
    }

    /*------------------------ FILL PREPARED PANES AND CHARTS ------------------------*/
    private void fillParamsTab(CustomTabPane customTabPane) {
//        TODO ADD IMPL

        textFieldSetValue(textFieldSignalAverageValue, "");
        textFieldSetValue(textFieldAbsoluteSignalAverageValue, "");
        textFieldSetValue(textFieldSignalEffectiveValue, "");
        textFieldSetValue(textFieldSignalVariance, "");
        textFieldSetValue(textFieldAverageSignalStrength, "");

//        textFieldSetValue(textFieldMediumSquareError, "");
//        textFieldSetValue(textFieldSignalNoiseRatio, "");
//        textFieldSetValue(textFieldPeakSignalNoiseRatio, "");
//        textFieldSetValue(textFieldMaximumDifference, "");
//        textFieldSetValue(textFieldEffectiveNumberOfBits, "");
//        textFieldSetValue(textFieldTransformationTime, "");
    }

    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Series lineChartCollection,
                                           Collection<ChartRecord<String, Number>> barChartCollection) {
        CustomTabPane customTabPane = castTabPaneToCustomTabPane(tabPane);

        fillLineChart(customTabPane, lineChartCollection);
        fillBarChart(customTabPane, barChartCollection);
        fillParamsTab(customTabPane);
    }

    @FXML
    private void onActionButtonGenerateData(ActionEvent actionEvent) {
        //TODO ADD IMPL
        String selectedSignal = comboBoxSignalTypes.getSelectionModel()
                .getSelectedItem().toString();

        String selectedOperation = comboBoxOperationTypes.getSelectionModel()
                .getSelectedItem().toString();

        lineChartData = Stream.of(
                new Data(1.0, 200.0),
                new Data(2.0, 500.0),
                new Data(3.0, 800.0),
                new Data(4.0, 100.0),
                new Data(5.0, 350.0),
                new Data(6.0, 400.0)
        ).collect(Collectors.toCollection(Series::new));

        barChartData = Stream.of(
                new ChartRecord<String, Number>("aa", 1),
                new ChartRecord<String, Number>("bb", 2),
                new ChartRecord<String, Number>("cc", 3),
                new ChartRecord<String, Number>("dd", 4),
                new ChartRecord<String, Number>("ee", 5),
                new ChartRecord<String, Number>("e", 6),
                new ChartRecord<String, Number>("f", 6),
                new ChartRecord<String, Number>("g", 6),
                new ChartRecord<String, Number>("h", 6),
                new ChartRecord<String, Number>("k", 6),
                new ChartRecord<String, Number>("i", 6)
        ).collect(Collectors.toCollection(ArrayList::new));

//        TODO IN FINAL VERSION MOVE TO IF STATEMENTS
        fillCustomTabPaneWithData(tabPaneResults, lineChartData, barChartData);
    }

    @FXML
    private void onActionHistogramRange(ActionEvent actionEvent) {
        Integer histogramRange = (Integer) spinnerHistogramRange.getValue();

        try {
            if (histogramRange <= barChartData.size()) {
                fillBarChart(castTabPaneToCustomTabPane(tabPaneResults), barChartData
                        .subList(0, histogramRange));
            } else {
                PopOutWindow.messageBox("Błędna Liczba Przedziałów",
                        "Histogram posiada zbyt małą liczbę przedziałów",
                        Alert.AlertType.WARNING);
            }
        } catch (NullPointerException e) {
            PopOutWindow.messageBox("Wygeneruj Wykresy",
                    "Wykresy nie zostały jeszcze wygenerowane",
                    Alert.AlertType.WARNING);
        }
    }
}
    