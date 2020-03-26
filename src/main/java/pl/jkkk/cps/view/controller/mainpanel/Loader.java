package pl.jkkk.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.OperationType;
import pl.jkkk.cps.logic.model.SignalType;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.GaussianNoise;
import pl.jkkk.cps.logic.model.signal.ImpulseNoise;
import pl.jkkk.cps.logic.model.signal.OperationResultSignal;
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
import pl.jkkk.cps.logic.reader.FileReader;
import pl.jkkk.cps.view.helper.DouglasPeuckerAlg;
import pl.jkkk.cps.view.model.ChartRecord;
import pl.jkkk.cps.view.model.CustomTabPane;
import pl.jkkk.cps.view.util.PopOutWindow;
import pl.jkkk.cps.view.util.StageController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.jkkk.cps.view.helper.ChartHelper.appendLabelText;
import static pl.jkkk.cps.view.helper.ChartHelper.castTabPaneToCustomTabPane;
import static pl.jkkk.cps.view.helper.ChartHelper.changeLineChartToScatterChart;
import static pl.jkkk.cps.view.helper.ChartHelper.changeScatterChartToLineChart;
import static pl.jkkk.cps.view.helper.ChartHelper.fillBarChart;
import static pl.jkkk.cps.view.helper.ChartHelper.fillLineChart;
import static pl.jkkk.cps.view.helper.ChartHelper.fillScatterChart;

public class Loader {

    /*------------------------ FIELDS REGION ------------------------*/
    private ComboBox comboBoxSignalTypes;
    private ComboBox comboBoxOperationTypes;
    private ComboBox comboBoxFirstSignal;
    private ComboBox comboBoxSecondSignal;

    private TextField textFieldAmplitude;
    private TextField textFieldStartTime;
    private TextField textFieldSignalDuration;
    private TextField textFieldBasicPeriod;
    private TextField textFieldFillFactor;
    private TextField textFieldJumpTime;
    private TextField textFieldProbability;
    private TextField textFieldSamplingFrequency;

    private TabPane tabPaneResults;
    private Spinner spinnerHistogramRange;

    private Map<Integer, Signal> signals = new HashMap<>();
    private FileReader<Signal> signalFileReader;
    private boolean isScatterChart;

    /*------------------------ METHODS REGION ------------------------*/
    public Loader(ComboBox comboBoxSignalTypes, ComboBox comboBoxOperationTypes,
                  ComboBox comboBoxFirstSignal, ComboBox comboBoxSecondSignal,
                  TextField textFieldAmplitude, TextField textFieldStartTime,
                  TextField textFieldSignalDuration, TextField textFieldBasicPeriod,
                  TextField textFieldFillFactor, TextField textFieldJumpTime,
                  TextField textFieldProbability, TextField textFieldSamplingFrequency,
                  TabPane tabPaneResults, Spinner spinnerHistogramRange) {
        this.comboBoxSignalTypes = comboBoxSignalTypes;
        this.comboBoxOperationTypes = comboBoxOperationTypes;
        this.comboBoxFirstSignal = comboBoxFirstSignal;
        this.comboBoxSecondSignal = comboBoxSecondSignal;
        this.textFieldAmplitude = textFieldAmplitude;
        this.textFieldStartTime = textFieldStartTime;
        this.textFieldSignalDuration = textFieldSignalDuration;
        this.textFieldBasicPeriod = textFieldBasicPeriod;
        this.textFieldFillFactor = textFieldFillFactor;
        this.textFieldJumpTime = textFieldJumpTime;
        this.textFieldProbability = textFieldProbability;
        this.textFieldSamplingFrequency = textFieldSamplingFrequency;
        this.tabPaneResults = tabPaneResults;
        this.spinnerHistogramRange = spinnerHistogramRange;
    }

    private void fillParamsTab(CustomTabPane customTabPane, double[] signalParams) {
        Pane pane = (Pane) customTabPane.getParamsTab().getContent();
        List<Node> paneChildren = pane.getChildren();

        DecimalFormat df = new DecimalFormat("##.####");
        appendLabelText(paneChildren.get(0), "" + df.format(signalParams[0]));
        appendLabelText(paneChildren.get(1), "" + df.format(signalParams[1]));
        appendLabelText(paneChildren.get(2), "" + df.format(signalParams[2]));
        appendLabelText(paneChildren.get(3), "" + df.format(signalParams[3]));
        appendLabelText(paneChildren.get(4), "" + df.format(signalParams[4]));
    }

    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Collection<ChartRecord<Number, Number>> mainChartData,
                                           Collection<ChartRecord<String, Number>> histogramData,
                                           double[] signalParams) {
        CustomTabPane customTabPane = castTabPaneToCustomTabPane(tabPane);

        if (isScatterChart) {
            fillScatterChart((ScatterChart) customTabPane.getChartTab()
                    .getContent(), mainChartData);
        } else {
            fillLineChart((LineChart) customTabPane.getChartTab().getContent(), mainChartData);
        }

        fillBarChart((BarChart) customTabPane.getHistogramTab().getContent(), histogramData);
        fillParamsTab(customTabPane, signalParams);
    }

    private void convertSignalToChart(Signal signal) {
        /* prepare line/point chart data */
        List<Data> data;
        if (signal instanceof GaussianNoise || signal instanceof UniformNoise) {
            data = new ArrayList<>();
            for (int i = 0; i < signal.getData().size(); i++) {
                if (i % (signal.getData().size() / 1000) == 0) {
                    data.add(signal.getData().get(i));
                }
            }
        } else if (signal instanceof ContinuousSignal || signal instanceof OperationResultSignal) {
            DouglasPeuckerAlg douglasPeucker = new DouglasPeuckerAlg();
            data = signal.getData();
            data = new ArrayList<>(douglasPeucker.calculate(
                    data,
                    (data.get(data.size() - 1).getX() - data.get(0).getX()) * 1.0 / 10000.0,
                    0,
                    data.size() - 1));
        } else {
            data = signal.getData();
        }

        System.out.println("Wygenerowanu punktów: " + data.size());

        List<ChartRecord<Number, Number>> chartData = data.stream()
                .map(d -> new ChartRecord<Number, Number>(d.getX(), d.getY()))
                .collect(Collectors.toList());

        /* prepare barchart data */
        DecimalFormat df = new DecimalFormat("#.##");
        List<ChartRecord<String, Number>> histogramData = signal
                .generateHistogram((int) spinnerHistogramRange.getValue())
                .stream()
                .map(range -> new ChartRecord<String, Number>(
                        df.format(range.getBegin()) + " do " + df.format(range.getEnd()),
                        range.getQuantity()))
                .collect(Collectors.toList());

        /* prepare params */
        double[] signalParams = new double[5];
        signalParams[0] = signal.meanValue();
        signalParams[1] = signal.absMeanValue();
        signalParams[2] = signal.rmsValue();
        signalParams[3] = signal.varianceValue();
        signalParams[4] = signal.meanPowerValue();

        /* render it all */
        fillCustomTabPaneWithData(tabPaneResults, chartData, histogramData, signalParams);
    }

    private void representSignal(Signal signal) {

        /* remember signal */
        int tabIndex = tabPaneResults.getSelectionModel().getSelectedIndex();
        signals.put(tabIndex, signal);

        /* generate signal */
        signal.generate();

        convertSignalToChart(signal);
    }

    public void computeCharts() {
        String selectedSignal = comboBoxSignalTypes.getSelectionModel()
                .getSelectedItem().toString();

        try {
            Double amplitude = Double.parseDouble(textFieldAmplitude.getText());
            Double rangeStart = Double.parseDouble(textFieldStartTime.getText());
            Double rangeLength = Double.parseDouble(textFieldSignalDuration.getText());
            Double term = Double.parseDouble(textFieldBasicPeriod.getText());
            Double fulfillment = Double.parseDouble(textFieldFillFactor.getText());
            Double jumpMoment = Double.parseDouble(textFieldJumpTime.getText());
            Double probability = Double.parseDouble(textFieldProbability.getText());
            Double sampleRate = Double.parseDouble(textFieldSamplingFrequency.getText());

            Signal signal = null;
            isScatterChart = false;
            changeScatterChartToLineChart(tabPaneResults);

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
                changeLineChartToScatterChart(tabPaneResults);

                signal = new UnitImpulseSignal(rangeStart, rangeLength, sampleRate,
                        amplitude, jumpMoment.intValue());

            } else if (selectedSignal.equals(SignalType.IMPULSE_NOISE.getName())) {

                isScatterChart = true;
                changeLineChartToScatterChart(tabPaneResults);

                signal = new ImpulseNoise(rangeStart, rangeLength, sampleRate, amplitude,
                        probability);

            }

            representSignal(signal);

        } catch (NumberFormatException e) {
            PopOutWindow.messageBox("Błędne Dane",
                    "Wprowadzono błędne dane", Alert.AlertType.WARNING);

        }
    }

    public void performOperationOnCharts() {
        String selectedOperation = comboBoxOperationTypes.getSelectionModel()
                .getSelectedItem().toString();
        int s1Index = comboBoxFirstSignal.getSelectionModel().getSelectedIndex();
        int s2Index = comboBoxSecondSignal.getSelectionModel().getSelectedIndex();

        Signal s1 = signals.get(s1Index);
        Signal s2 = signals.get(s2Index);
        Signal resultSignal = null;

        if (selectedOperation.equals(OperationType.ADDITION.getName())) {
            resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a + b);
        } else if (selectedOperation.equals(OperationType.SUBTRACTION.getName())) {
            resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a - b);
        } else if (selectedOperation.equals(OperationType.MULTIPLICATION.getName())) {
            resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a * b);
        } else if (selectedOperation.equals(OperationType.DIVISION.getName())) {
            resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a / b);
        }

        representSignal(resultSignal);
    }

    public void loadChart() {
        int tabIndex = tabPaneResults.getSelectionModel().getSelectedIndex();

        try {
            signalFileReader = new FileReader<>(new FileChooser()
                    .showOpenDialog(StageController.getApplicationStage())
                    .getName());

            signals.put(tabIndex, signalFileReader.read());
            convertSignalToChart(signals.get(tabIndex));

        } catch (NullPointerException | FileOperationException e) {
            PopOutWindow.messageBox("Błąd Ładowania Pliku",
                    "Nie można załadować wybranego pliku", Alert.AlertType.WARNING);
        }
    }

    public void saveChart() {
        int tabIndex = tabPaneResults.getSelectionModel().getSelectedIndex();

        try {
            if (signals.get(tabIndex) != null) {
                signalFileReader = new FileReader<>(new FileChooser()
                        .showSaveDialog(StageController.getApplicationStage())
                        .getName());

                signalFileReader.write(signals.get(tabIndex));
            } else {
                PopOutWindow.messageBox("Błąd Zapisu Do Pliku",
                        "Sygnał nie został jeszcze wygenerowany", Alert.AlertType.WARNING);
            }
        } catch (NullPointerException | FileOperationException e) {
            PopOutWindow.messageBox("Błąd Zapisu Do Pliku",
                    "Nie można zapisać do wybranego pliku", Alert.AlertType.WARNING);
        }
    }
}
