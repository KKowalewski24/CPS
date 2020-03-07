package pl.jkkk.cps.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;

public class MainPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    private static final int NUMBER_OF_TABS = 7;

    @FXML
    private TabPane tabPaneParams;
    @FXML
    private TabPane tabPaneCharts;

    /*------------------------ METHODS REGION ------------------------*/
    private void prepareTabPaneParams() {

    }

    //TODO FINISH IMPL
    private void prepareTabPaneCharts(int index) {
        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        Tab chartTab = new Tab("Wykres", lineChart);

        Tab histogramTab = new Tab();

        Tab paramsTab = new Tab();

        TabPane innerTabPane = new TabPane(chartTab, histogramTab, paramsTab);
        tabPaneCharts.getTabs().add(new Tab("Karta " + index, innerTabPane));
    }

    @FXML
    private void onActionButtonReloadStage(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
    }

    @FXML
    private void onActionButtonAddNewTab(ActionEvent actionEvent) {
        prepareTabPaneCharts(tabPaneCharts.getTabs().size());
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
    