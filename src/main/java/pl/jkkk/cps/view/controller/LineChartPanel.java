package pl.jkkk.cps.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareDataRecord;

public class LineChartPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    @FXML
    private LineChart<Number, Number> lineChart;

    /*------------------------ METHODS REGION ------------------------*/

    //TODO IMPROVE
    private void drawChart() {
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

        lineChart.getData().add(series);
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawChart();
    }
}
    