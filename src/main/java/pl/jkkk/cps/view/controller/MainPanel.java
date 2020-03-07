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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import pl.jkkk.cps.view.helper.CustomTab;
import pl.jkkk.cps.view.helper.CustomTabPane;
import pl.jkkk.cps.view.util.PopOutWindow;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;

public class MainPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    private static final List<String> signalTypeList = Stream.of(
            "szum o rozkładzie jednostajnym",
            "szum gaussowski",
            "sygnał sinusoidalny",
            "sygnał sinusoidalny wyprostowany jednopołówkowo",
            "sygnał sinusoidalny wyprostowany dwupołówkowo",
            "sygnał prostokątny",
            "sygnał prostokątny symetryczny",
            "sygnał trójkątny",
            "skok jednostkowy",
            "impuls jednostkowy",
            "szum impulsowy"
    ).collect(Collectors.toCollection(ArrayList::new));

    private static final List<String> operationTypeList = Stream.of(
            "dodawanie",
            "odejmowanie",
            "mnożenie",
            "dzielenie"
    ).collect(Collectors.toCollection(ArrayList::new));

    @FXML
    private ComboBox comboBoxSignalTypes;
    @FXML
    private ComboBox comboBoxOperationTypes;
    @FXML
    private TabPane tabPaneCharts;

    private LineChart<Number, Number> lineChart = new LineChart<>(
            new NumberAxis(), new NumberAxis());
    private BarChart<String, Number> barChart = new BarChart<>(
            new CategoryAxis(), new NumberAxis());

    /*------------------------ METHODS REGION ------------------------*/
    private void prepareTabPaneParams() {
        signalTypeList.forEach((it) -> comboBoxSignalTypes.getItems().add(it));
        operationTypeList.forEach((it) -> comboBoxOperationTypes.getItems().add(it));
        comboBoxSignalTypes.getSelectionModel().selectFirst();
        comboBoxOperationTypes.getSelectionModel().selectFirst();
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

        if (selectedSignal.equals(signalTypeList.get(0))) {

        } else if (selectedSignal.equals(signalTypeList.get(1))) {

        } else if (selectedSignal.equals(signalTypeList.get(2))) {

        } else if (selectedSignal.equals(signalTypeList.get(3))) {

        } else if (selectedSignal.equals(signalTypeList.get(4))) {

        } else if (selectedSignal.equals(signalTypeList.get(5))) {

        } else if (selectedSignal.equals(signalTypeList.get(6))) {

        } else if (selectedSignal.equals(signalTypeList.get(7))) {

        } else if (selectedSignal.equals(signalTypeList.get(8))) {

        } else if (selectedSignal.equals(signalTypeList.get(9))) {

        } else if (selectedSignal.equals(signalTypeList.get(10))) {

        } else if (selectedSignal.equals(signalTypeList.get(11))) {

        }
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareTabPaneParams();
        prepareTabPaneCharts(0);
    }
}
    