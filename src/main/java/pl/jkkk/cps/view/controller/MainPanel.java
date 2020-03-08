package pl.jkkk.cps.view.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import pl.jkkk.cps.view.helper.ChartRecord;
import pl.jkkk.cps.view.helper.CustomTab;
import pl.jkkk.cps.view.helper.CustomTabPane;
import pl.jkkk.cps.view.util.PopOutWindow;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.constant.Constants.OPERATION_TYPE_LIST;
import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.SIGNAL_TYPE_LIST;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;
import static pl.jkkk.cps.view.helper.ChartHelper.fillComboBox;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareDataRecord;
import static pl.jkkk.cps.view.helper.ChartHelper.textFieldSetValue;

public class MainPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
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
    @FXML
    private TabPane tabPaneCharts;

    /*------------------------ METHODS REGION ------------------------*/
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

    private void prepareTabPaneParams() {
        fillComboBox(comboBoxSignalTypes, SIGNAL_TYPE_LIST);
        fillComboBox(comboBoxOperationTypes, OPERATION_TYPE_LIST);

        fillTextFields();
    }

    private void prepareTabPaneCharts(int index) {
        tabPaneCharts.getTabs().add(new Tab("Karta " + index,
                new CustomTabPane(
                        new CustomTab("Wykres", new LineChart<>(new NumberAxis(),
                                new NumberAxis()), false),
                        new CustomTab("Histogram", new BarChart<>(new CategoryAxis(),
                                new NumberAxis()), false),
                        new CustomTab("Parametry", null, false)
                )));
    }

    private void fillLineChart(CustomTabPane customTabPane,
                               Collection<ChartRecord<Number, Number>> dataCollection) {
        LineChart lineChart = (LineChart) customTabPane.getChartTab().getContent();
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getxAxis(), it.getyAxis()));
        });

        lineChart.getData().clear();
        lineChart.getData().add(series);
    }

    private void fillBarChart(CustomTabPane customTabPane,
                              Collection<ChartRecord<String, Number>> dataCollection) {
        BarChart barChart = (BarChart) customTabPane.getHistogramTab().getContent();
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getxAxis(), it.getyAxis()));
        });

        barChart.getData().clear();
        barChart.getData().add(series);

    }

    private void fillParamsTab() {
//        TODO ADD IMPL
    }

    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Collection<ChartRecord<Number, Number>> lineChartCollection,
                                           Collection<ChartRecord<String, Number>> barChartCollection) {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        CustomTabPane customTabPane = (CustomTabPane) tab.getContent();

        fillLineChart(customTabPane, lineChartCollection);
        fillBarChart(customTabPane, barChartCollection);
        fillParamsTab();
    }

    /*------------------------ BUTTON BAR ------------------------*/
    @FXML
    private void onActionButtonReloadStage(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
    }

    @FXML
    private void onActionButtonAddNewTab(ActionEvent actionEvent) {
        ObservableList<Tab> tabList = tabPaneCharts.getTabs();
        if (tabList.size() != 0) {
            prepareTabPaneCharts(tabList.size());
        } else {
            PopOutWindow.messageBox("Brak Wykresów",
                    "Wykresy nie zostały jeszcze wygenerowane", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onActionButtonCloseProgram(ActionEvent actionEvent) {
        System.exit(0);
    }

    /*------------------------ FILE ------------------------*/
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

    @FXML
    private void onActionButtonGenerateData(ActionEvent actionEvent) {
        //TODO ADD IMPL
        String selectedSignal = comboBoxSignalTypes.getSelectionModel()
                .getSelectedItem().toString();

        String selectedOperation = comboBoxOperationTypes.getSelectionModel()
                .getSelectedItem().toString();

        if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(0))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(1))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(2))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(3))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(4))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(5))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(6))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(7))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(8))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(9))) {

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(10))) {

        }

        if (selectedOperation.equals(OPERATION_TYPE_LIST.get(0))) {

        } else if (selectedOperation.equals(OPERATION_TYPE_LIST.get(1))) {

        } else if (selectedOperation.equals(OPERATION_TYPE_LIST.get(2))) {

        } else if (selectedOperation.equals(OPERATION_TYPE_LIST.get(3))) {

        }

//        TODO IN FINAL VERSION MOVE TO IF STATEMENTS
        fillCustomTabPaneWithData(tabPaneCharts,
                Stream.of(
                        new ChartRecord<Number, Number>(1, 200),
                        new ChartRecord<Number, Number>(2, 500),
                        new ChartRecord<Number, Number>(3, 800),
                        new ChartRecord<Number, Number>(4, 100),
                        new ChartRecord<Number, Number>(5, 350),
                        new ChartRecord<Number, Number>(6, 400)
                ).collect(Collectors.toCollection(ArrayList::new)),
                Stream.of(
                        new ChartRecord<String, Number>("aa", 1),
                        new ChartRecord<String, Number>("bb", 2),
                        new ChartRecord<String, Number>("cc", 3),
                        new ChartRecord<String, Number>("dd", 4),
                        new ChartRecord<String, Number>("ee", 5)
                ).collect(Collectors.toCollection(ArrayList::new)));
    }

    /*------------------------ MAIN METHOD ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareTabPaneParams();
        prepareTabPaneCharts(0);
    }
}
    