package pl.jkkk.cps.view.controller.mainpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;
import static pl.jkkk.cps.view.helper.ChartHelper.fillComboBox;
import static pl.jkkk.cps.view.helper.ChartHelper.getSelectedTabIndex;
import static pl.jkkk.cps.view.helper.ChartHelper.getTabNameList;

public class MainPanel implements Initializable {

    public AnchorPane oneArgsPane;
    /*------------------------ FIELDS REGION ------------------------*/
    @FXML
    private TabPane tabPaneInputs;
    @FXML
    private Pane chooseParamsTab;
    @FXML
    private ComboBox comboBoxSignalTypes;
    @FXML
    private ComboBox comboBoxOperationTypesTwoArgs;
    @FXML
    private ComboBox comboBoxFirstSignalTwoArgs;
    @FXML
    private ComboBox comboBoxSecondSignalTwoArgs;
    @FXML
    private TabPane tabPaneResults;
    @FXML
    private Spinner spinnerHistogramRange;

    @FXML
    private ComboBox comboBoxOperationTypesOneArgs;
    @FXML
    private ComboBox comboBoxSignalOneArgs;
    @FXML
    private ComboBox comboBoxComparisonFirstSignal;
    @FXML
    private ComboBox comboBoxComparisonSecondSignal;
    @FXML
    private AnchorPane comparisonPane;

    private TextField textFieldAmplitude = new TextField();
    private TextField textFieldStartTime = new TextField();
    private TextField textFieldSignalDuration = new TextField();
    private TextField textFieldBasicPeriod = new TextField();
    private TextField textFieldFillFactor = new TextField();
    private TextField textFieldJumpTime = new TextField();
    private TextField textFieldProbability = new TextField();
    private TextField textFieldSamplingFrequency = new TextField();
    private TextField textFieldQuantizationLevels = new TextField();

    private Initializer initializer;
    private Loader loader;

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerHistogramRange.setValueFactory(new SpinnerValueFactory
                .IntegerSpinnerValueFactory(5, 20, 10, 5));

        initializer = new Initializer(
                comboBoxSignalTypes, comboBoxOperationTypesTwoArgs, comboBoxFirstSignalTwoArgs,
                comboBoxSecondSignalTwoArgs, chooseParamsTab, textFieldAmplitude,
                textFieldStartTime, textFieldSignalDuration, textFieldBasicPeriod,
                textFieldFillFactor, textFieldJumpTime, textFieldProbability,
                textFieldSamplingFrequency, tabPaneResults,
                comboBoxOperationTypesOneArgs, comboBoxSignalOneArgs,
                comboBoxComparisonFirstSignal, comboBoxComparisonSecondSignal,
                comparisonPane, oneArgsPane, textFieldQuantizationLevels
        );

        loader = new Loader(
                comboBoxSignalTypes, comboBoxOperationTypesTwoArgs, comboBoxFirstSignalTwoArgs,
                comboBoxSecondSignalTwoArgs, textFieldAmplitude, textFieldStartTime,
                textFieldSignalDuration, textFieldBasicPeriod, textFieldFillFactor,
                textFieldJumpTime, textFieldProbability, textFieldSamplingFrequency,
                tabPaneResults, spinnerHistogramRange,
                comboBoxOperationTypesOneArgs, comboBoxSignalOneArgs,
                comboBoxComparisonFirstSignal, comboBoxComparisonSecondSignal,
                comparisonPane, oneArgsPane, textFieldQuantizationLevels
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

        fillComboBox(comboBoxFirstSignalTwoArgs, getTabNameList(tabPaneResults.getTabs()));
        fillComboBox(comboBoxSecondSignalTwoArgs, getTabNameList(tabPaneResults.getTabs()));

        fillComboBox(comboBoxSignalOneArgs, getTabNameList(tabPaneResults.getTabs()));

        fillComboBox(comboBoxComparisonFirstSignal, getTabNameList(tabPaneResults.getTabs()));
        fillComboBox(comboBoxComparisonSecondSignal, getTabNameList(tabPaneResults.getTabs()));
    }

    @FXML
    private void onActionButtonCloseProgram(ActionEvent actionEvent) {
        System.exit(0);
    }

    /*--------------------------------------------------------------------------------------------*/
    @FXML
    private void onActionLoadChart(ActionEvent actionEvent) {
        loader.loadChart();
    }

    @FXML
    private void onActionSaveChart(ActionEvent actionEvent) {
        loader.saveChart();
    }

    /*--------------------------------------------------------------------------------------------*/
    @FXML
    private void onActionButtonGenerateData(ActionEvent actionEvent) {
        Integer selectedTab = getSelectedTabIndex(tabPaneInputs);

        switch (selectedTab) {
            case 0: {
                loader.computeCharts();
                break;
            }
            case 1: {
                loader.performOneArgsOperationOnCharts();
                break;
            }
            case 2: {
                loader.performTwoArgsOperationOnCharts();
                break;
            }
        }
    }

    @FXML
    private void onActionButtonGenerateComparison(ActionEvent actionEvent) {
        loader.generateComparison();
    }
}
