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
    private LineChart lineChart;

    /*------------------------ METHODS REGION ------------------------*/

    //TODO IMPROVE
    private void drawChart() {
        XYChart.Series series = new XYChart.Series<>();
        series.getData().addAll(
                prepareDataRecord("aaa", 200),
                prepareDataRecord("bbb", 500),
                prepareDataRecord("ccc", 800),
                prepareDataRecord("ddd", 100),
                prepareDataRecord("eee", 350),
                prepareDataRecord("fff", 400),
                prepareDataRecord("ggg", 550)
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
    