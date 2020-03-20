package pl.jkkk.cps.view.controller.mainpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import pl.jkkk.cps.logic.model.Series;
import pl.jkkk.cps.view.helper.ChartRecord;
import pl.jkkk.cps.view.util.PopOutWindow;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;
import static pl.jkkk.cps.view.helper.ChartHelper.fillComboBox;
import static pl.jkkk.cps.view.helper.ChartHelper.getTabNameList;

public class MainPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    @FXML
    private TabPane tabPaneInputs;
    @FXML
    private Pane chooseParamsTab;
    @FXML
    private ComboBox comboBoxSignalTypes;
    @FXML
    private ComboBox comboBoxOperationTypes;
    @FXML
    private ComboBox comboBoxFirstSignal;
    @FXML
    private ComboBox comboBoxSecondSignal;
    @FXML
    private TabPane tabPaneResults;
    @FXML
    private Spinner spinnerHistogramRange;

    private TextField textFieldAmplitude = new TextField();
    private TextField textFieldStartTime = new TextField();
    private TextField textFieldSignalDuration = new TextField();
    private TextField textFieldBasicPeriod = new TextField();
    private TextField textFieldFillFactor = new TextField();
    private TextField textFieldJumpTime = new TextField();
    private TextField textFieldProbability = new TextField();
    private TextField textFieldSamplingFrequency = new TextField();

    private Initializer initializer;
    private Loader loader;

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerHistogramRange.setValueFactory(new SpinnerValueFactory
                .IntegerSpinnerValueFactory(5, 20, 10, 5));

        initializer = new Initializer(
                comboBoxSignalTypes, comboBoxOperationTypes, comboBoxFirstSignal,
                comboBoxSecondSignal, chooseParamsTab, textFieldAmplitude,
                textFieldStartTime, textFieldSignalDuration, textFieldBasicPeriod,
                textFieldFillFactor, textFieldJumpTime, textFieldProbability,
                textFieldSamplingFrequency, tabPaneResults
        );

        loader = new Loader(
                comboBoxSignalTypes, comboBoxOperationTypes, comboBoxFirstSignal,
                comboBoxSecondSignal, textFieldAmplitude, textFieldStartTime,
                textFieldSignalDuration, textFieldBasicPeriod, textFieldFillFactor,
                textFieldJumpTime, textFieldProbability, textFieldSamplingFrequency,
                tabPaneResults, spinnerHistogramRange
        );

        initializer.prepareTabPaneResults(0);
        initializer.prepareTabPaneInputs();
    }

    /*--------------------------------------------------------------------------------------------*/
    @FXML
    private void onActionButtonReloadStage(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
    }

    @FXML
    private void onActionButtonAddNewTab(ActionEvent actionEvent) {
        initializer.prepareTabPaneResults(tabPaneResults.getTabs().size());
        fillComboBox(comboBoxFirstSignal, getTabNameList(tabPaneResults.getTabs()));
        fillComboBox(comboBoxSecondSignal, getTabNameList(tabPaneResults.getTabs()));
    }

    @FXML
    private void onActionButtonCloseProgram(ActionEvent actionEvent) {
        System.exit(0);
    }

    /*--------------------------------------------------------------------------------------------*/
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

    /*--------------------------------------------------------------------------------------------*/
    @FXML
    private void onActionButtonGenerateData(ActionEvent actionEvent) {
        //TODO ADD IMPL, IN FINAL VERSION LOAD DATA FROM LOGIC
        Integer selectedTab = tabPaneInputs.getSelectionModel().getSelectedIndex();

        switch (selectedTab) {
            case 0: {
                loader.computeCharts();
                break;
            }
            case 1: {
                loader.performOperationOnCharts();
                break;
            }
        }
    }
}
