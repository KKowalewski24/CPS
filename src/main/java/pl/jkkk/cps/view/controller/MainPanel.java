package pl.jkkk.cps.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import pl.jkkk.cps.logic.model.OperationType;
import pl.jkkk.cps.logic.model.Series;
import pl.jkkk.cps.logic.model.SignalType;
import pl.jkkk.cps.logic.model.signal.GaussianNoise;
import pl.jkkk.cps.logic.model.signal.ImpulseNoise;
import pl.jkkk.cps.logic.model.signal.RectangularSignal;
import pl.jkkk.cps.logic.model.signal.RectangularSymmetricSignal;
import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.model.signal.SinusoidalRectifiedOneHalfSignal;
import pl.jkkk.cps.logic.model.signal.SinusoidalRectifiedTwoHalfSignal;
import pl.jkkk.cps.logic.model.signal.SinusoidalSignal;
import pl.jkkk.cps.logic.model.signal.TriangularSignal;
import pl.jkkk.cps.logic.model.signal.UniformNoise;
import pl.jkkk.cps.logic.model.signal.UnitImpulseSignal;
import pl.jkkk.cps.logic.model.signal.UnitJumpSignal;
import pl.jkkk.cps.view.helper.ChartRecord;
import pl.jkkk.cps.view.helper.CustomTab;
import pl.jkkk.cps.view.helper.CustomTabPane;
import pl.jkkk.cps.view.util.PopOutWindow;
import pl.jkkk.cps.view.util.StageController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.constant.Constants.PATH_MAIN_PANEL;
import static pl.jkkk.cps.view.constant.Constants.TITLE_MAIN_PANEL;
import static pl.jkkk.cps.view.helper.ChartHelper.appendLabelText;
import static pl.jkkk.cps.view.helper.ChartHelper.castTabPaneToCustomTabPane;
import static pl.jkkk.cps.view.helper.ChartHelper.changeLineChartToScatterChart;
import static pl.jkkk.cps.view.helper.ChartHelper.fillBarChart;
import static pl.jkkk.cps.view.helper.ChartHelper.fillComboBox;
import static pl.jkkk.cps.view.helper.ChartHelper.fillLineChart;
import static pl.jkkk.cps.view.helper.ChartHelper.fillScatterChart;
import static pl.jkkk.cps.view.helper.ChartHelper.getTabNameList;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareLabelWithPosition;
import static pl.jkkk.cps.view.helper.ChartHelper.setTextFieldPosition;
import static pl.jkkk.cps.view.helper.ChartHelper.textFieldSetValue;

public class MainPanel implements Initializable {

    /*------------------------ FIELDS REGION ------------------------*/
    /* LEFT SIDE */
    @FXML
    private TabPane tabPaneInputs;
    @FXML
    private ComboBox comboBoxSignalTypes;
    @FXML
    private Pane chooseParamsTab;

    private TextField textFieldAmplitude = new TextField();
    private TextField textFieldStartTime = new TextField();
    private TextField textFieldSignalDuration = new TextField();
    private TextField textFieldBasicPeriod = new TextField();
    private TextField textFieldFillFactor = new TextField();
    private TextField textFieldJumpTime = new TextField();
    private TextField textFieldProbability = new TextField();
    private TextField textFieldSamplingFrequency = new TextField();

    @FXML
    private ComboBox comboBoxOperationTypes;
    @FXML
    private ComboBox comboBoxFirstSignal;
    @FXML
    private ComboBox comboBoxSecondSignal;

    /* RIGHT SIDE */
    @FXML
    private TabPane tabPaneResults;
    @FXML
    private Spinner spinnerHistogramRange;

    /* OTHER FIELDS */
    private Series chartData;
    private List<ChartRecord<String, Number>> histogramData;
    private boolean isScatterChart;

    /*------------------------ METHODS REGION ------------------------*/

    /*------------------------ PREPARE EMPTY PANES AND CHARTS ------------------------*/
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

    private void fillComboBoxes() {
        fillComboBox(comboBoxSignalTypes, Stream.of(
                SignalType.UNIFORM_NOISE.getName(),
                SignalType.GAUSSIAN_NOISE.getName(),
                SignalType.SINUSOIDAL_SIGNAL.getName(),
                SignalType.SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL.getName(),
                SignalType.SINUSOIDAL_RECTIFIED_IN_TWO_HALVES.getName(),
                SignalType.RECTANGULAR_SIGNAL.getName(),
                SignalType.SYMMETRICAL_RECTANGULAR_SIGNAL.getName(),
                SignalType.TRIANGULAR_SIGNAL.getName(),
                SignalType.UNIT_JUMP.getName(),
                SignalType.UNIT_IMPULSE.getName(),
                SignalType.IMPULSE_NOISE.getName()
        ).collect(Collectors.toCollection(ArrayList::new)));

        fillComboBox(comboBoxOperationTypes, Stream.of(
                OperationType.ADDITION.getName(),
                OperationType.SUBTRACTION.getName(),
                OperationType.MULTIPLICATION.getName(),
                OperationType.DIVISION.getName()
        ).collect(Collectors.toCollection(ArrayList::new)));

        fillComboBox(comboBoxFirstSignal, getTabNameList(tabPaneResults.getTabs()));
        fillComboBox(comboBoxSecondSignal, getTabNameList(tabPaneResults.getTabs()));
    }

    private void fillChooseParamsTab() {

        List<Node> basicInputs = Stream.of(
                prepareLabelWithPosition("Wybierz Parametry", 168, 14),
                prepareLabelWithPosition("Amplituda", 50, 50),
                prepareLabelWithPosition("Czas początkowy", 50, 90),
                prepareLabelWithPosition("Czas trwania sygnału", 50, 130),
                setTextFieldPosition(textFieldAmplitude, 270, 50),
                setTextFieldPosition(textFieldStartTime, 270, 90),
                setTextFieldPosition(textFieldSignalDuration, 270, 130)
        ).collect(Collectors.toCollection(ArrayList::new));

        chooseParamsTab.getChildren().setAll(basicInputs);

        comboBoxSignalTypes.setOnAction((event -> {
            String selectedSignal = comboBoxSignalTypes.getSelectionModel()
                    .getSelectedItem().toString();

            if (selectedSignal.equals(SignalType.UNIFORM_NOISE.getName())
                    || selectedSignal.equals(SignalType.GAUSSIAN_NOISE.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);

            } else if (selectedSignal.equals(SignalType.SINUSOIDAL_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_IN_TWO_HALVES.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        prepareLabelWithPosition("Okres podstawowy", 50, 170),
                        setTextFieldPosition(textFieldBasicPeriod, 270, 170)
                );
            } else if (selectedSignal.equals(SignalType.RECTANGULAR_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.SYMMETRICAL_RECTANGULAR_SIGNAL.getName())
                    || selectedSignal.equals(SignalType.TRIANGULAR_SIGNAL.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        prepareLabelWithPosition("Okres podstawowy", 50, 170),
                        setTextFieldPosition(textFieldBasicPeriod, 270, 170),
                        prepareLabelWithPosition("Wspł wypełnienia", 50, 210),
                        setTextFieldPosition(textFieldFillFactor, 270, 210)
                );
            } else if (selectedSignal.equals(SignalType.UNIT_JUMP.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        prepareLabelWithPosition("Czas skoku", 50, 170),
                        setTextFieldPosition(textFieldJumpTime, 270, 170)
                );
            } else if (selectedSignal.equals(SignalType.UNIT_IMPULSE.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        prepareLabelWithPosition("Częst próbkowania", 50, 170),
                        prepareLabelWithPosition("Numer próbki skoku", 50, 210),
                        setTextFieldPosition(textFieldSamplingFrequency, 270, 170),
                        setTextFieldPosition(textFieldJumpTime, 270, 210)
                );
            } else if (selectedSignal.equals(SignalType.IMPULSE_NOISE.getName())) {

                chooseParamsTab.getChildren().setAll(basicInputs);
                chooseParamsTab.getChildren().addAll(
                        prepareLabelWithPosition("Prawdopodobieństwo", 50, 170),
                        prepareLabelWithPosition("Częst próbkowania", 50, 210),
                        setTextFieldPosition(textFieldProbability, 270, 170),
                        setTextFieldPosition(textFieldSamplingFrequency, 270, 210)
                );
            }
        }));
    }

    private void prepareTabPaneInputs() {
        fillComboBoxes();
        fillTextFields();
        fillChooseParamsTab();
    }

    private void prepareTabPaneResults(int index) {

        LineChart lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);

        BarChart barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setAnimated(true);

        Pane pane = new Pane(
                prepareLabelWithPosition("Wartość średnia sygnału: ", 25, 40),
                prepareLabelWithPosition("Wartość średnia bezwzględna sygnału: ", 25, 80),
                prepareLabelWithPosition("Wartość skuteczna sygnału: ", 25, 120),
                prepareLabelWithPosition("Wariancja sygnału: ", 25, 160),
                prepareLabelWithPosition("Moc średnia sygnału: ", 25, 200),
                prepareLabelWithPosition("Błąd średniokwadratowy: ", 25, 240),
                prepareLabelWithPosition("Stosunek sygnał - szum: ", 25, 280),
                prepareLabelWithPosition("Szczytowy stosunek sygnał - szum: ", 25, 320),
                prepareLabelWithPosition("Maksymalna różnica: ", 25, 360),
                prepareLabelWithPosition("Efektywna liczba bitów: ", 25, 400),
                prepareLabelWithPosition("Czas transformacji: ", 25, 440)
        );

        tabPaneResults.getTabs().add(new Tab("Karta " + index,
                new CustomTabPane(
                        new CustomTab("Wykres", lineChart, false),
                        new CustomTab("Histogram", barChart, false),
                        new CustomTab("Parametry", pane, false)
                )));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerHistogramRange.setValueFactory(new SpinnerValueFactory
                .IntegerSpinnerValueFactory(5, 20, 10, 5));

        prepareTabPaneResults(0);
        prepareTabPaneInputs();
    }

    /*------------------------ BUTTON TOP BAR ------------------------*/
    @FXML
    private void onActionButtonReloadStage(ActionEvent actionEvent) {
        StageController.reloadStage(PATH_MAIN_PANEL, TITLE_MAIN_PANEL);
    }

    @FXML
    private void onActionButtonAddNewTab(ActionEvent actionEvent) {
        prepareTabPaneResults(tabPaneResults.getTabs().size());
        fillComboBox(comboBoxFirstSignal, getTabNameList(tabPaneResults.getTabs()));
        fillComboBox(comboBoxSecondSignal, getTabNameList(tabPaneResults.getTabs()));
    }

    @FXML
    private void onActionButtonCloseProgram(ActionEvent actionEvent) {
        System.exit(0);
    }

    /*------------------------ FILE READ / WRITE ------------------------*/
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

    /*------------------------ FILL PREPARED PANES AND CHARTS ------------------------*/
    private void fillParamsTab(CustomTabPane customTabPane) {
        Pane pane = (Pane) customTabPane.getParamsTab().getContent();
        List<Node> paneChildren = pane.getChildren();

//        TODO ADD IMPL
        appendLabelText(paneChildren.get(0), "PUT REAL DATA FOR ALL");
    }

    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Series chartCollection,
                                           Collection<ChartRecord<String, Number>> barChartCollection) {
        CustomTabPane customTabPane = castTabPaneToCustomTabPane(tabPane);

        if (isScatterChart) {
            fillScatterChart(customTabPane, chartCollection);
        } else {
            fillLineChart(customTabPane, chartCollection);
        }
        fillBarChart(customTabPane, barChartCollection);
        fillParamsTab(customTabPane);
    }

    @FXML
    private void onActionButtonGenerateData(ActionEvent actionEvent) {
        //TODO ADD IMPL, IN FINAL VERSION LOAD DATA FROM LOGIC
        Integer selectedTab = tabPaneInputs.getSelectionModel().getSelectedIndex();

        switch (selectedTab) {
            case 0: {
                String selectedSignal = comboBoxSignalTypes.getSelectionModel()
                        .getSelectedItem().toString();

                /* get signal params */
                //TODO MOVE TO METHOD
                double amplitude = Double.parseDouble(textFieldAmplitude.getText());
                double rangeStart = Double.parseDouble(textFieldStartTime.getText());
                double rangeLength = Double.parseDouble(textFieldSignalDuration.getText());
                double term = Double.parseDouble(textFieldBasicPeriod.getText());
                double fulfillment = Double.parseDouble(textFieldFillFactor.getText());
                double jumpMoment = Double.parseDouble(textFieldJumpTime.getText());
                double propability = Double.parseDouble(textFieldProbability.getText());
                double sampleRate = Double.parseDouble(textFieldSamplingFrequency.getText());

                /* Create proper signal */
                Signal signal = null;
                if (selectedSignal.equals(SignalType.UNIFORM_NOISE.getName())) {

                    signal = new UniformNoise(rangeStart, rangeLength, amplitude);

                } else if (selectedSignal.equals(SignalType.GAUSSIAN_NOISE.getName())) {

                    signal = new GaussianNoise(rangeStart, rangeLength, amplitude);

                } else if (selectedSignal.equals(SignalType.SINUSOIDAL_SIGNAL.getName())) {

                    signal = new SinusoidalSignal(rangeStart, rangeLength, amplitude, term);

                } else if (selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL.getName())) {

                    signal = new SinusoidalRectifiedOneHalfSignal(rangeStart, rangeLength,
                            amplitude, term);

                } else if (selectedSignal.equals(SignalType.SINUSOIDAL_RECTIFIED_IN_TWO_HALVES.getName())) {

                    signal = new SinusoidalRectifiedTwoHalfSignal(rangeStart, rangeLength,
                            amplitude, term);

                } else if (selectedSignal.equals(SignalType.RECTANGULAR_SIGNAL.getName())) {

                    signal = new RectangularSignal(rangeStart, rangeLength, amplitude, term,
                            fulfillment);

                } else if (selectedSignal.equals(SignalType.SYMMETRICAL_RECTANGULAR_SIGNAL.getName())) {

                    signal = new RectangularSymmetricSignal(rangeStart, rangeLength, amplitude,
                            term, fulfillment);

                } else if (selectedSignal.equals(SignalType.TRIANGULAR_SIGNAL.getName())) {

                    signal = new TriangularSignal(rangeStart, rangeLength, amplitude, term,
                            fulfillment);

                } else if (selectedSignal.equals(SignalType.UNIT_JUMP.getName())) {

                    signal = new UnitJumpSignal(rangeStart, rangeLength, amplitude, jumpMoment);

                } else if (selectedSignal.equals(SignalType.UNIT_IMPULSE.getName())) {

                    isScatterChart = true;
                    changeLineChartToScatterChart(tabPaneResults,
                            new ScatterChart(new NumberAxis(), new NumberAxis()));

                    signal = new UnitImpulseSignal(rangeStart, rangeLength, sampleRate, amplitude,
                            (int) jumpMoment);

                } else if (selectedSignal.equals(SignalType.IMPULSE_NOISE.getName())) {

                    isScatterChart = true;
                    changeLineChartToScatterChart(tabPaneResults,
                            new ScatterChart(new NumberAxis(), new NumberAxis()));

                    signal = new ImpulseNoise(rangeStart, rangeLength, sampleRate, amplitude,
                            propability);

                }
                chartData = new Series();
                chartData.addAll(signal.generate());

                histogramData = Stream.of(
                        new ChartRecord<String, Number>("aa", 1),
                        new ChartRecord<String, Number>("bb", 2),
                        new ChartRecord<String, Number>("cc", 3),
                        new ChartRecord<String, Number>("dd", 4),
                        new ChartRecord<String, Number>("ee", 5),
                        new ChartRecord<String, Number>("e", 6),
                        new ChartRecord<String, Number>("f", 6),
                        new ChartRecord<String, Number>("g", 6),
                        new ChartRecord<String, Number>("h", 6),
                        new ChartRecord<String, Number>("k", 6),
                        new ChartRecord<String, Number>("i", 6)
                ).collect(Collectors.toCollection(ArrayList::new));

                fillCustomTabPaneWithData(tabPaneResults, chartData, histogramData);

                break;
            }
            case 1: {
                String selectedOperation = comboBoxOperationTypes.getSelectionModel()
                        .getSelectedItem().toString();

                break;
            }
        }
    }

    @FXML
    private void onActionHistogramRange(ActionEvent actionEvent) {
        Integer histogramRange = (Integer) spinnerHistogramRange.getValue();

        try {
            if (histogramRange <= histogramData.size()) {
                fillBarChart(castTabPaneToCustomTabPane(tabPaneResults), histogramData
                        .subList(0, histogramRange));
            } else {
                PopOutWindow.messageBox("Błędna Liczba Przedziałów",
                        "Histogram posiada zbyt małą liczbę przedziałów",
                        Alert.AlertType.WARNING);
            }
        } catch (NullPointerException e) {
            PopOutWindow.messageBox("Wygeneruj Wykresy",
                    "Wykresy nie zostały jeszcze wygenerowane",
                    Alert.AlertType.WARNING);
        }
    }
}
    
