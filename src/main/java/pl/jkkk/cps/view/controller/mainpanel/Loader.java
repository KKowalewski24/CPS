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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.exception.NotSameLengthException;
import pl.jkkk.cps.logic.model.ADC;
import pl.jkkk.cps.logic.model.DAC;
import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.SignalComparator;
import pl.jkkk.cps.logic.model.enumtype.OneArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.QuantizationType;
import pl.jkkk.cps.logic.model.enumtype.SignalReconstructionType;
import pl.jkkk.cps.logic.model.enumtype.SignalType;
import pl.jkkk.cps.logic.model.enumtype.TwoArgsOperationType;
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

import static pl.jkkk.cps.view.helper.FxHelper.appendLabelText;
import static pl.jkkk.cps.view.helper.FxHelper.getCurrentCustomTabPaneFromTabPane;
import static pl.jkkk.cps.view.helper.FxHelper.changeLineChartToScatterChart;
import static pl.jkkk.cps.view.helper.FxHelper.changeScatterChartToLineChart;
import static pl.jkkk.cps.view.helper.FxHelper.fillBarChart;
import static pl.jkkk.cps.view.helper.FxHelper.fillLineChart;
import static pl.jkkk.cps.view.helper.FxHelper.fillScatterChart;
import static pl.jkkk.cps.view.helper.FxHelper.getIndexFromComboBox;
import static pl.jkkk.cps.view.helper.FxHelper.getSelectedTabIndex;
import static pl.jkkk.cps.view.helper.FxHelper.getValueFromComboBox;

public class Loader {

    /*------------------------ FIELDS REGION ------------------------*/
    private ComboBox comboBoxSignalTypes;
    private ComboBox comboBoxOperationTypesTwoArgs;
    private ComboBox comboBoxFirstSignalTwoArgs;
    private ComboBox comboBoxSecondSignalTwoArgs;

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

    private ComboBox comboBoxOperationTypesOneArgs;
    private ComboBox comboBoxSignalOneArgs;
    private ComboBox comboBoxComparisonFirstSignal;
    private ComboBox comboBoxComparisonSecondSignal;
    private AnchorPane comparisonPane;
    private AnchorPane oneArgsPane;
    private TextField textFieldQuantizationLevels;
    private TextField textFieldSampleRate;
    private TextField textFieldReconstructionSincParam;

    private Map<Integer, Signal> signals = new HashMap<>();
    private FileReader<Signal> signalFileReader;
    private boolean isScatterChart;
    private SignalComparator signalComparator = new SignalComparator();

    /*------------------------ METHODS REGION ------------------------*/
    public Loader(ComboBox comboBoxSignalTypes, ComboBox comboBoxOperationTypesTwoArgs,
                  ComboBox comboBoxFirstSignalTwoArgs, ComboBox comboBoxSecondSignalTwoArgs,
                  TextField textFieldAmplitude, TextField textFieldStartTime,
                  TextField textFieldSignalDuration, TextField textFieldBasicPeriod,
                  TextField textFieldFillFactor, TextField textFieldJumpTime,
                  TextField textFieldProbability, TextField textFieldSamplingFrequency,
                  TabPane tabPaneResults, Spinner spinnerHistogramRange,
                  ComboBox comboBoxOperationTypesOneArgs, ComboBox comboBoxSignalOneArgs,
                  ComboBox comboBoxComparisonFirstSignal,
                  ComboBox comboBoxComparisonSecondSignal,
                  AnchorPane comparisonPane, AnchorPane oneArgsPane,
                  TextField textFieldQuantizationLevels, TextField textFieldSampleRate,
                  TextField textFieldReconstructionSincParam) {
        this.comboBoxSignalTypes = comboBoxSignalTypes;
        this.comboBoxOperationTypesTwoArgs = comboBoxOperationTypesTwoArgs;
        this.comboBoxFirstSignalTwoArgs = comboBoxFirstSignalTwoArgs;
        this.comboBoxSecondSignalTwoArgs = comboBoxSecondSignalTwoArgs;
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
        this.comboBoxOperationTypesOneArgs = comboBoxOperationTypesOneArgs;
        this.comboBoxSignalOneArgs = comboBoxSignalOneArgs;
        this.comboBoxComparisonFirstSignal = comboBoxComparisonFirstSignal;
        this.comboBoxComparisonSecondSignal = comboBoxComparisonSecondSignal;
        this.comparisonPane = comparisonPane;
        this.oneArgsPane = oneArgsPane;
        this.textFieldQuantizationLevels = textFieldQuantizationLevels;
        this.textFieldSampleRate = textFieldSampleRate;
        this.textFieldReconstructionSincParam = textFieldReconstructionSincParam;
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
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);

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
        int tabIndex = getSelectedTabIndex(tabPaneResults);
        signals.put(tabIndex, signal);

        /* generate signal */
        signal.generate();

        convertSignalToChart(signal);
    }

    public void computeCharts() {
        String selectedSignal = getValueFromComboBox(comboBoxSignalTypes);

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

    public void performOneArgsOperationOnCharts() {
        Signal signal = null;
        String selectedOperationOneArgs = getValueFromComboBox(comboBoxOperationTypesOneArgs);
        Integer selectedSignalIndex = getIndexFromComboBox(comboBoxSignalOneArgs);
        Signal selectedSignal = signals.get(selectedSignalIndex);

        try {
            //            todo obliczanie czasu
            if (selectedOperationOneArgs.equals(OneArgsOperationType.SAMPLING.getName())) {
                Integer sampleRate = Integer.valueOf(textFieldSampleRate.getText());
                //todo wykres punktowy
                if (selectedSignal instanceof ContinuousSignal) {
                    signal = new ADC().sampling((ContinuousSignal) selectedSignal, sampleRate);
                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType.QUANTIZATION.getName())) {
                //todo wykres punktowy
                Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
                ComboBox comboBoxMethod = (ComboBox) topPane.getChildren().get(1);

                Integer quantizationLevels = Integer.valueOf(textFieldQuantizationLevels.getText());
                String method = getValueFromComboBox(comboBoxMethod);

                if (method.equals(QuantizationType.EVEN_QUANTIZATION_WITH_TRUNCATION.getName())) {
                    signal = new ADC().truncatingQuantization(selectedSignal, quantizationLevels);
                } else if (method.equals(QuantizationType.EVEN_QUANTIZATION_WITH_ROUNDING.getName())) {
                    signal = new ADC().roundingQuantization(selectedSignal, quantizationLevels);
                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType.SIGNAL_RECONSTRUCTION.getName())) {
                Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
                ComboBox comboBoxMethod = (ComboBox) topPane.getChildren().get(1);

                String method = getValueFromComboBox(comboBoxMethod);
                //todo wykres liniowy
                if (method.equals(SignalReconstructionType.ZERO_ORDER_EXTRAPOLATION.getName())) {

                    signal = new DAC().zeroOrderHold(selectedSignal);

                } else if (method.equals(SignalReconstructionType.FIRST_ORDER_INTERPOLATION.getName())) {

                    signal = new DAC().firstOrderHold(selectedSignal);

                } else if (method.equals(SignalReconstructionType
                        .RECONSTRUCTION_BASED_FUNCTION_SINC.getName())) {

                    Integer sincParam = Integer.valueOf(textFieldReconstructionSincParam.getText());
                    //                    todo
                    //                signal =
                }
            }

            representSignal(signal);

        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            PopOutWindow.messageBox("Błędne Dane",
                    "Wprowadzono błędne dane", Alert.AlertType.WARNING);
        }
    }

    public void performTwoArgsOperationOnCharts() {
        String selectedOperation = getValueFromComboBox(comboBoxOperationTypesTwoArgs);

        int s1Index = getIndexFromComboBox(comboBoxFirstSignalTwoArgs);
        int s2Index = getIndexFromComboBox(comboBoxSecondSignalTwoArgs);

        Signal s1 = signals.get(s1Index);
        Signal s2 = signals.get(s2Index);
        Signal resultSignal = null;

        if (selectedOperation.equals(TwoArgsOperationType.ADDITION.getName())) {
            resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a + b);
        } else if (selectedOperation.equals(TwoArgsOperationType.SUBTRACTION.getName())) {
            resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a - b);
        } else if (selectedOperation.equals(TwoArgsOperationType.MULTIPLICATION.getName())) {
            resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a * b);
        } else if (selectedOperation.equals(TwoArgsOperationType.DIVISION.getName())) {
            resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a / b);
        }

        representSignal(resultSignal);
    }

    public void generateComparison() {
        Integer selectedTab1Index = getIndexFromComboBox(comboBoxComparisonFirstSignal);
        Integer selectedTab2Index = getIndexFromComboBox(comboBoxComparisonSecondSignal);
        List<Node> paneChildren = comparisonPane.getChildren();

        Signal firstSignal = signals.get(selectedTab1Index);
        Signal secondSignal = signals.get(selectedTab2Index);
        DecimalFormat df = new DecimalFormat("##.####");

        try {
            appendLabelText(paneChildren.get(0),
                    "" + df.format(signalComparator.meanSquaredError(secondSignal, firstSignal)));
            appendLabelText(paneChildren.get(1),
                    "" + df.format(signalComparator.signalToNoiseRatio(secondSignal, firstSignal)));
            appendLabelText(paneChildren.get(2),
                    "" + df.format(signalComparator.peakSignalToNoiseRatio(secondSignal,
                            firstSignal)));
            appendLabelText(paneChildren.get(3),
                    "" + df.format(signalComparator.maximumDifference(secondSignal, firstSignal)));

            //        TODO
            //        appendLabelText(paneChildren.get(4),
            //                "" + df.format(signalComparator.(secondSignal, firstSignal)));
            //        appendLabelText(paneChildren.get(5),
            //                "" + df.format(signalComparator.(secondSignal, firstSignal)));
        } catch (NotSameLengthException e) {
            PopOutWindow.messageBox("Błednie wybrane wykresy",
                    "Wykresy mają błędnie dobraną długość", Alert.AlertType.WARNING);
        }
    }

    public void loadChart() {
        int tabIndex = getSelectedTabIndex(tabPaneResults);

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
        int tabIndex = getSelectedTabIndex(tabPaneResults);

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
