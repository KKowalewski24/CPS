package pl.jkkk.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pl.jkkk.cps.logic.model.enumtype.OneArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.QuantizationType;
import pl.jkkk.cps.logic.model.enumtype.SignalReconstructionType;
import pl.jkkk.cps.logic.model.enumtype.SignalType;
import pl.jkkk.cps.logic.model.enumtype.TwoArgsOperationType;
import pl.jkkk.cps.view.model.CustomTab;
import pl.jkkk.cps.view.model.CustomTabPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.helper.ChartHelper.fillComboBox;
import static pl.jkkk.cps.view.helper.ChartHelper.getTabNameList;
import static pl.jkkk.cps.view.helper.ChartHelper.getValueFromComboBox;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareBarChart;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareLabelWithPosition;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareLineChart;
import static pl.jkkk.cps.view.helper.ChartHelper.setTextFieldPosition;
import static pl.jkkk.cps.view.helper.ChartHelper.textFieldSetValue;

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
                       AnchorPane oneArgsPane, TextField textFieldQuantizationLevels) {
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

    /*--------------------------------------------------------------------------------------------*/
    private void fillOneArgsTab() {
        fillComboBox(comboBoxOperationTypesOneArgs, Stream.of(
                OneArgsOperationType.SAMPLING.getName(),
                OneArgsOperationType.QUANTIZATION.getName(),
                OneArgsOperationType.SIGNAL_RECONSTRUCTION.getName()
        ).collect(Collectors.toCollection(ArrayList::new)));

        textFieldSetValue(textFieldQuantizationLevels, String.valueOf(10));
        fillComboBox(comboBoxSignalOneArgs, getTabNameList(tabPaneResults.getTabs()));
        oneArgsPane.setVisible(false);

        /*-----  -----*/
        comboBoxOperationTypesOneArgs.setOnAction((event -> {
            String selectedOperation = getValueFromComboBox(comboBoxOperationTypesOneArgs);

            if (selectedOperation.equals(OneArgsOperationType.SAMPLING.getName())) {
                oneArgsPane.setVisible(false);
            } else {
                oneArgsPane.setVisible(true);

                Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
                ComboBox comboBoxMethod = (ComboBox) topPane.getChildren().get(1);

                Pane bottomPane = (Pane) oneArgsPane.getChildren().get(1);
                bottomPane.setVisible(false);

                if (selectedOperation.equals(OneArgsOperationType.QUANTIZATION.getName())) {

                    fillComboBox(comboBoxMethod, Stream.of(
                            QuantizationType.EVEN_QUANTIZATION_WITH_TRUNCATION.getName(),
                            QuantizationType.EVEN_QUANTIZATION_WITH_ROUNDING.getName()
                    ).collect(Collectors.toCollection(ArrayList::new)));

                    bottomPane.setVisible(true);

                    if (bottomPane.getChildren().size() == 1) {
                        bottomPane.getChildren()
                                .add(setTextFieldPosition(textFieldQuantizationLevels, 250, 30));
                    }

                } else if (selectedOperation.equals(OneArgsOperationType.SIGNAL_RECONSTRUCTION.getName())) {

                    fillComboBox(comboBoxMethod, Stream.of(
                            SignalReconstructionType.ZERO_ORDER_EXTRAPOLATION.getName(),
                            SignalReconstructionType.FIRST_ORDER_INTERPOLATION.getName(),
                            SignalReconstructionType.RECONSTRUCTION_BASED_FUNCTION_SINC.getName()
                    ).collect(Collectors.toCollection(ArrayList::new)));

                }
            }
        }));
    }

    /*--------------------------------------------------------------------------------------------*/
    private void fillTwoArgsTab() {
        fillComboBox(comboBoxOperationTypesTwoArgs, Stream.of(
                TwoArgsOperationType.ADDITION.getName(),
                TwoArgsOperationType.SUBTRACTION.getName(),
                TwoArgsOperationType.MULTIPLICATION.getName(),
                TwoArgsOperationType.DIVISION.getName()
        ).collect(Collectors.toCollection(ArrayList::new)));

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
                        new CustomTab("Parametry", pane, false)
                )));
    }
}
