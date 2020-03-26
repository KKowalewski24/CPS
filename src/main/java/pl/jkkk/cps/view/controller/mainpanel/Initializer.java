package pl.jkkk.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import pl.jkkk.cps.logic.model.OperationType;
import pl.jkkk.cps.logic.model.SignalType;
import pl.jkkk.cps.view.model.CustomTab;
import pl.jkkk.cps.view.model.CustomTabPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.helper.ChartHelper.fillComboBox;
import static pl.jkkk.cps.view.helper.ChartHelper.getTabNameList;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareBarChart;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareLabelWithPosition;
import static pl.jkkk.cps.view.helper.ChartHelper.prepareLineChart;
import static pl.jkkk.cps.view.helper.ChartHelper.setTextFieldPosition;
import static pl.jkkk.cps.view.helper.ChartHelper.textFieldSetValue;

public class Initializer {

    /*------------------------ FIELDS REGION ------------------------*/
    private ComboBox comboBoxSignalTypes;
    private ComboBox comboBoxOperationTypes;
    private ComboBox comboBoxFirstSignal;
    private ComboBox comboBoxSecondSignal;
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

    /*------------------------ METHODS REGION ------------------------*/
    public Initializer(ComboBox comboBoxSignalTypes, ComboBox comboBoxOperationTypes,
                       ComboBox comboBoxFirstSignal, ComboBox comboBoxSecondSignal,
                       Pane chooseParamsTab, TextField textFieldAmplitude,
                       TextField textFieldStartTime, TextField textFieldSignalDuration,
                       TextField textFieldBasicPeriod, TextField textFieldFillFactor,
                       TextField textFieldJumpTime, TextField textFieldProbability,
                       TextField textFieldSamplingFrequency, TabPane tabPaneResults) {
        this.comboBoxSignalTypes = comboBoxSignalTypes;
        this.comboBoxOperationTypes = comboBoxOperationTypes;
        this.comboBoxFirstSignal = comboBoxFirstSignal;
        this.comboBoxSecondSignal = comboBoxSecondSignal;
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
    }

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

    public void prepareTabPaneInputs() {
        fillComboBoxes();
        fillTextFields();
        fillChooseParamsTab();
    }

    public void prepareTabPaneResults(int index) {
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
                        new CustomTab("Wykres", prepareLineChart(), false),
                        new CustomTab("Histogram", prepareBarChart(), false),
                        new CustomTab("Parametry", pane, false)
                )));
    }
}
