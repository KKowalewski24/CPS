package pl.jkkk.cps.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.jkkk.cps.view.constant.Constants.PATH_BAR_CHART_PANEL;
import static pl.jkkk.cps.view.constant.Constants.PATH_LINE_CHART_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_BAR_CHART_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_LINE_CHART_PANEL;

public class MainPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    @FXML
    private void onActionButtonBarChart(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_BAR_CHART_PANEL, TITLE_BAR_CHART_PANEL);
    }

    @FXML
    private void onActionButtonLineChart(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_LINE_CHART_PANEL, TITLE_LINE_CHART_PANEL);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
    