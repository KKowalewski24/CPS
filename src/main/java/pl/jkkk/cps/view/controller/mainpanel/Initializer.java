package pl.jkkk.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pl.jkkk.cps.logic.model.enumtype.AlgorithmType;
import pl.jkkk.cps.logic.model.enumtype.OneArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.QuantizationType;
import pl.jkkk.cps.logic.model.enumtype.SignalReconstructionType;
import pl.jkkk.cps.logic.model.enumtype.SignalType;
import pl.jkkk.cps.logic.model.enumtype.TwoArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.WaveletType;
import pl.jkkk.cps.logic.model.enumtype.WindowType;
import pl.jkkk.cps.view.model.CustomTab;
import pl.jkkk.cps.view.model.CustomTabPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.fxml.FxHelper.fillComboBox;
import static pl.jkkk.cps.view.fxml.FxHelper.getTabNameList;
import static pl.jkkk.cps.view.fxml.FxHelper.getValueFromComboBox;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareBarChart;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareLabelWithPosition;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.removeAndAddNewPaneChildren;
import static pl.jkkk.cps.view.fxml.FxHelper.setPaneVisibility;
import static pl.jkkk.cps.view.fxml.FxHelper.setTextFieldPosition;
import static pl.jkkk.cps.view.fxml.FxHelper.textFieldSetValue;

public class Initializer {

    /*------------------------ FIELDS REGION ------------------------*/
    private ComboBox comboBoxSignalTypes;
    private ComboBox comboBoxOperationTypesTwoArgs;
    private ComboBox comboBoxFirstSignalTwoArgs;
    private ComboBox comboBoxSecondSignalTwoArgs;
    private Pane chooseParamsTab;

    private TextField textFieldAmplitude;
    private TextField textFieldStartTime;
    private TextField textFieldSignalDuration;
    private TextField textFieldBasicPeriod;
    private TextField textFieldFillFactor;
    private TextField textFieldJumpTime;
    private TextField textFieldProbability;
    private TextField textFieldSamplingFrequency;

    private TabPane tabPaneResults;

    private ComboBox comboBoxOperationTypesOneArgs;
    private ComboBox comboBoxSignalOneArgs;
    private ComboBox comboBoxComparisonFirstSignal;
    private ComboBox comboBoxComparisonSecondSignal;
    private AnchorPane comparisonPane;
    private AnchorPane oneArgsPane;
    private TextField textFieldQuantizationLevels;
    private TextField textFieldSampleRate;
    private TextField textFieldReconstructionSincParam;
    private AnchorPane windowTypePane;
    private TextField textFieldCuttingFrequency;
    private TextField textFieldFilterRow;

    /*------------------------ METHODS REGION ------------------------*/
    public Initializer(ComboBox comboBoxSignalTypes, ComboBox comboBoxOperationTypesTwoArgs,
                       ComboBox comboBoxFirstSignalTwoArgs, ComboBox comboBoxSecondSignalTwoArgs,
                       Pane chooseParamsTab, TextField textFieldAmplitude,
                       TextField textFieldStartTime, TextField textFieldSignalDuration,
                       TextField textFieldBasicPeriod, TextField textFieldFillFactor,
                       TextField textFieldJumpTime, TextField textFieldProbability,
                       TextField textFieldSamplingFrequency, TabPane tabPaneResults,
                       ComboBox comboBoxOperationTypesOneArgs, ComboBox comboBoxSignalOneArgs,
                       ComboBox comboBoxComparisonFirstSignal,
                       ComboBox comboBoxComparisonSecondSignal, AnchorPane comparisonPane,
                       AnchorPane oneArgsPane, TextField textFieldQuantizationLevels,
                       TextField textFieldSampleRate, TextField textFieldReconstructionSincParam,
                       AnchorPane windowTypePane, TextField textFieldCuttingFrequency,
                       TextField textFieldFilterRow) {
        this.comboBoxSignalTypes = comboBoxSignalTypes;
        this.comboBoxOperationTypesTwoArgs = comboBoxOperationTypesTwoArgs;
        this.comboBoxFirstSignalTwoArgs = comboBoxFirstSignalTwoArgs;
        this.comboBoxSecondSignalTwoArgs = comboBoxSecondSignalTwoArgs;
        this.chooseParamsTab = chooseParamsTab;
        this.textFieldAmplitude = textFieldAmplitude;
        this.textFieldStartTime = textFieldStartTime;
        this.textFieldSignalDuration = textFieldSignalDuration;
        this.textFieldBasicPeriod = textFieldBasicPeriod;
        this.textFieldFillFactor = textFieldFillFactor;
        this.textFieldJumpTime = textFieldJumpTime;
        this.textFieldProbability = textFieldProbability;
        this.textFieldSamplingFrequency = textFieldSamplingFrequency;
        this.tabPaneResults = tabPaneResults;
        this.comboBoxOperationTypesOneArgs = comboBoxOperationTypesOneArgs;
        this.comboBoxSignalOneArgs = comboBoxSignalOneArgs;
        this.comboBoxComparisonFirstSignal = comboBoxComparisonFirstSignal;
        this.comboBoxComparisonSecondSignal = comboBoxComparisonSecondSignal;
        this.comparisonPane = comparisonPane;
        this.oneArgsPane = oneArgsPane;
        this.textFieldQuantizationLevels = textFieldQuantizationLevels;
        this.textFieldSampleRate = textFieldSampleRate;
        this.textFieldReconstructionSincParam = textFieldReconstructionSincParam;
        this.windowTypePane = windowTypePane;
        this.textFieldCuttingFrequency = textFieldCuttingFrequency;
        this.textFieldFilterRow = textFieldFilterRow;
    }

    /*--------------------------------------------------------------------------------------------*/
    public void prepareTabPaneInputs() {
        fillGenerationTab();
        fillOneArgsTab();
        fillTwoArgsTab();
        fillComparisonTab();
    }

    public void prepareTabPaneResults(int index) {
        Pane pane = new Pane(
                prepareLabelWithPosition("Wartość średnia sygnału: ", 25, 40),
                prepareLabelWithPosition("Wartość średnia bezwzględna sygnału: ", 25, 80),
                prepareLabelWithPosition("Wartość skuteczna sygnału: ", 25, 120),
                prepareLabelWithPosition("Wariancja sygnału: ", 25, 160),
                prepareLabelWithPosition("Moc średnia sygnału: ", 25, 200)
        );

        tabPaneResults.getTabs().add(new Tab("Karta " + index,
                new CustomTabPane(
                        new CustomTab("Wykres", prepareLineChart(), false),
                        new CustomTab("Histogram", prepareBarChart(), false),
                        new CustomTab("Parametry", pane, false),
                        new CustomTab("W1", new VBox(
                                prepareLineChart(
                                        "Część rzeczywista amplitudy w funkcji częstotliwości"),
                                prepareLineChart(
                                        "Część urojona amplitudy w funkcji częstotliwości")),
                                false),
                        new CustomTab("W2", new VBox(
                                prepareLineChart("Moduł liczby zespolonej"),
                                prepareLineChart("Argument liczby w funkcji częstotliwości")),
                                false)
                )));
    }

    /*--------------------------------------------------------------------------------------------*/
    private void fillGenerationTab() {
        textFieldSetValue(textFieldAmplitude, String.valueOf(1));
        textFieldSetValue(textFieldStartTime, String.valueOf(0));
        textFieldSetValue(textFieldSignalDuration, String.valueOf(5));
        textFieldSetValue(textFieldBasicPeriod, String.valueOf(1));
        textFieldSetValue(textFieldFillFactor, String.valueOf(0.5));
        textFieldSetValue(textFieldJumpTime, String.valueOf(2));
        textFieldSetValue(textFieldProbability, String.valueOf(0.5));
        textFieldSetValue(textFieldSamplingFrequency, String.valueOf(16));
        textFieldSetValue(textFieldCuttingFrequency, String.valueOf(4));
        textFieldSetValue(textFieldFilterRow, String.valueOf(15));

        fillComboBox(comboBoxSignalTypes, SignalType.getNamesList());

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
        setPaneVisibility(false, windowTypePane);

        comboBoxSignalTypes.setOnAction((event -> {
            String selectedSignal = getValueFromComboBox(comboBoxSignalTypes);
            setPaneVisibility(false, windowTypePane);

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
            } else if (selectedSignal.equals(SignalType.LOW_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.BAND_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.HIGH_PASS_FILTER.getName())) {

                chooseParamsTab.getChildren().setAll(
                        prepareLabelWithPosition("Wybierz Parametry", 168, 14),
                        prepareLabelWithPosition("Częstotliwość próbkowania", 50, 50),
                        prepareLabelWithPosition("Rząd filtra", 50, 90),
                        prepareLabelWithPosition("Częstotliwość odcięcia", 50, 130),
                        setTextFieldPosition(textFieldSamplingFrequency, 270, 50),
                        setTextFieldPosition(textFieldFilterRow, 270, 90),
                        setTextFieldPosition(textFieldCuttingFrequency, 270, 130)
                );

                ComboBox comboBoxWindowType = (ComboBox) windowTypePane.getChildren().get(1);
                fillComboBox(comboBoxWindowType, WindowType.getNamesList());
                setPaneVisibility(true, windowTypePane);
            }
        }));
    }

    /*--------------------------------------------------------------------------------------------*/
    private void fillOneArgsTab() {
        fillComboBox(comboBoxOperationTypesOneArgs, OneArgsOperationType.getNamesList());
        textFieldSetValue(textFieldQuantizationLevels, String.valueOf(10));
        textFieldSetValue(textFieldSampleRate, String.valueOf(10));
        textFieldSetValue(textFieldReconstructionSincParam, String.valueOf(1));
        fillComboBox(comboBoxSignalOneArgs, getTabNameList(tabPaneResults.getTabs()));

        final Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
        final Pane middlePane = (Pane) oneArgsPane.getChildren().get(1);
        final Pane bottomPane = (Pane) oneArgsPane.getChildren().get(2);

        setPaneVisibility(false, topPane, middlePane);
        removeAndAddNewPaneChildren(bottomPane,
                prepareLabelWithPosition("Częstotliwość próbkowania", 14, 33),
                setTextFieldPosition(textFieldSampleRate, 250, 30)
        );

        comboBoxOperationTypesOneArgs.setOnAction((event ->
                actionComboBoxOperationTypesOneArgs(topPane, middlePane, bottomPane)));
    }

    private void actionComboBoxOperationTypesOneArgs(Pane topPane,
                                                     Pane middlePane, Pane bottomPane) {
        final String methodLabelValue = "Wybierz Metodę";
        final String algorithmLabelValue = "Wybierz Typ Algorytmu";
        final String algorithmLevelLabelValue = "Wybierz Poziom";

        final Label labelMethodOrAlgorithm = (Label) topPane.getChildren().get(0);
        final ComboBox comboBoxMethodOrAlgorithm = (ComboBox) topPane.getChildren().get(1);

        String selectedOperation = getValueFromComboBox(comboBoxOperationTypesOneArgs);
        setPaneVisibility(false, topPane, middlePane);

        if (selectedOperation.equals(OneArgsOperationType.SAMPLING.getName())) {
            setPaneVisibility(true, bottomPane);

            removeAndAddNewPaneChildren(bottomPane,
                    prepareLabelWithPosition("Częstotliwość próbkowania", 14, 33),
                    setTextFieldPosition(textFieldSampleRate, 250, 30)
            );

        } else if (selectedOperation.equals(OneArgsOperationType.QUANTIZATION.getName())
                || selectedOperation.equals(OneArgsOperationType.SIGNAL_RECONSTRUCTION.getName())) {

            labelMethodOrAlgorithm.setText(methodLabelValue);
            setPaneVisibility(true, topPane, bottomPane);
            setPaneVisibility(false, middlePane);

            if (selectedOperation.equals(OneArgsOperationType.QUANTIZATION.getName())) {
                fillComboBox(comboBoxMethodOrAlgorithm, QuantizationType.getNamesList());
                removeAndAddNewPaneChildren(bottomPane,
                        prepareLabelWithPosition("Liczba Poziomów Kwantyzacji", 14, 33),
                        setTextFieldPosition(textFieldQuantizationLevels, 250, 30)
                );
            } else if (selectedOperation.equals(OneArgsOperationType.SIGNAL_RECONSTRUCTION.getName())) {
                fillComboBox(comboBoxMethodOrAlgorithm, SignalReconstructionType.getNamesList());
                removeAndAddNewPaneChildren(bottomPane,
                        prepareLabelWithPosition("Parametr funkcji sinc", 14, 33),
                        setTextFieldPosition(textFieldReconstructionSincParam,
                                250, 30)
                );
            }
        } else if (selectedOperation.equals(OneArgsOperationType.DISCRETE_FOURIER_TRANSFORMATION.getName())
                || selectedOperation.equals(OneArgsOperationType.COSINE_TRANSFORMATION.getName())
                || selectedOperation.equals(OneArgsOperationType.WALSH_HADAMARD_TRANSFORMATION.getName())
                || selectedOperation.equals(OneArgsOperationType.WAVELET_TRANSFORMATION.getName())) {

            setPaneVisibility(false, bottomPane);
            setPaneVisibility(true, topPane, middlePane);

            labelMethodOrAlgorithm.setText(algorithmLabelValue);

            if (!selectedOperation.equals(OneArgsOperationType.WAVELET_TRANSFORMATION.getName())) {
                fillComboBox(comboBoxMethodOrAlgorithm, AlgorithmType.getNamesList());
            }

            if (selectedOperation.equals(OneArgsOperationType
                    .WAVELET_TRANSFORMATION.getName())) {
                fillComboBox(comboBoxMethodOrAlgorithm, WaveletType.getNamesList());
                labelMethodOrAlgorithm.setText(algorithmLevelLabelValue);
            }
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    private void fillTwoArgsTab() {
        fillComboBox(comboBoxOperationTypesTwoArgs, TwoArgsOperationType.getNamesList());
        fillComboBox(comboBoxFirstSignalTwoArgs, getTabNameList(tabPaneResults.getTabs()));
        fillComboBox(comboBoxSecondSignalTwoArgs, getTabNameList(tabPaneResults.getTabs()));
    }

    /*--------------------------------------------------------------------------------------------*/
    private void fillComparisonTab() {
        fillComboBox(comboBoxComparisonFirstSignal, getTabNameList(tabPaneResults.getTabs()));
        fillComboBox(comboBoxComparisonSecondSignal, getTabNameList(tabPaneResults.getTabs()));

        comparisonPane.getChildren().addAll(
                prepareLabelWithPosition("Błąd średniokwadratowy: ", 25, 20),
                prepareLabelWithPosition("Stosunek sygnał - szum: ", 25, 60),
                prepareLabelWithPosition("Szczytowy stosunek sygnał - szum: ", 25, 100),
                prepareLabelWithPosition("Maksymalna różnica: ", 25, 140),
                prepareLabelWithPosition("Efektywna liczba bitów: ", 25, 180),
                prepareLabelWithPosition("Czas transformacji: ", 25, 220)
        );
    }
}
