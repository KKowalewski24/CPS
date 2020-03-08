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
import pl.jkkk.cps.view.helper.CustomTab;
import pl.jkkk.cps.view.helper.CustomTabPane;
import pl.jkkk.cps.view.util.PopOutWindow;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ResourceBundle;

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

        } else if (selectedSignal.equals(SIGNAL_TYPE_LIST.get(11))) {

        }

        Tab tab = tabPaneCharts.getSelectionModel().getSelectedItem();
        CustomTabPane customTabPane = (CustomTabPane) tab.getContent();
        LineChart lineChart = (LineChart) customTabPane.getChartTab().getContent();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().addAll(
                prepareDataRecord(1, 200),
                prepareDataRecord(2, 500),
                prepareDataRecord(3, 800),
                prepareDataRecord(4, 100),
                prepareDataRecord(5, 350),
                prepareDataRecord(6, 400),
                prepareDataRecord(7, 550)
        );

        lineChart.getData().clear();
        lineChart.getData().add(series);
    }

    /*------------------------ MAIN METHOD ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareTabPaneParams();
        prepareTabPaneCharts(0);
    }
}
    