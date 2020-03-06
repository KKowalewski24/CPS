package pl.jkkk.cps.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareDataRecord;

public class BarChartPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    @FXML
    private BarChart<String, Number> barChart;

    /*------------------------ METHODS REGION ------------------------*/

    //TODO IMPROVE
    private void drawChart() {
        String austria = "Austria";
        String brazil = "Brazil";
        String france = "France";
        String italy = "Italy";
        String usa = "USA";

        XYChart.Series<String, Number> series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().addAll(
                prepareDataRecord(austria, 25601.34),
                prepareDataRecord(brazil, 20148.82),
                prepareDataRecord(france, 10000),
                prepareDataRecord(italy, 35407.15),
                prepareDataRecord(usa, 12000)
        );

        XYChart.Series<String, Number> series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().addAll(
                prepareDataRecord(austria, 57401.85),
                prepareDataRecord(brazil, 41941.19),
                prepareDataRecord(france, 45263.37),
                prepareDataRecord(italy, 117320.16),
                prepareDataRecord(usa, 14845.27)
        );

        XYChart.Series<String, Number> series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().addAll(
                prepareDataRecord(austria, 45000.65),
                prepareDataRecord(brazil, 44835.76),
                prepareDataRecord(france, 18722.18),
                prepareDataRecord(italy, 17557.31),
                prepareDataRecord(usa, 92633.68)
        );

        barChart.getData().addAll(series1, series2, series3);
    }

    @FXML
    private void onActionReturn(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
    }

    @FXML
    private void onActionDrawChart(ActionEvent actionEvent) {
        drawChart();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawChart();
    }
}
    