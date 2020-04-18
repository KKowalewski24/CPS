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
import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.exception.NotSameLengthException;
import pl.jkkk.cps.logic.model.ADC;
import pl.jkkk.cps.logic.model.DAC;
import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.enumtype.OneArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.QuantizationType;
import pl.jkkk.cps.logic.model.enumtype.SignalReconstructionType;
import pl.jkkk.cps.logic.model.enumtype.SignalType;
import pl.jkkk.cps.logic.model.enumtype.TwoArgsOperationType;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.ConvolutionSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
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
import pl.jkkk.cps.logic.readerwriter.FileReaderWriter;
import pl.jkkk.cps.logic.readerwriter.ReportWriter;
import pl.jkkk.cps.logic.report.LatexGenerator;
import pl.jkkk.cps.logic.report.ReportType;
import pl.jkkk.cps.view.fxml.DouglasPeuckerAlg;
import pl.jkkk.cps.view.fxml.PopOutWindow;
import pl.jkkk.cps.view.fxml.StageController;
import pl.jkkk.cps.view.model.ChartRecord;
import pl.jkkk.cps.view.model.CustomTabPane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.jkkk.cps.view.fxml.FxHelper.appendLabelText;
import static pl.jkkk.cps.view.fxml.FxHelper.changeLineChartToScatterChart;
import static pl.jkkk.cps.view.fxml.FxHelper.changeScatterChartToLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.clearAndFillBarChart;
import static pl.jkkk.cps.view.fxml.FxHelper.clearAndFillLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.clearAndFillScatterChart;
import static pl.jkkk.cps.view.fxml.FxHelper.getCurrentCustomTabPaneFromTabPane;
import static pl.jkkk.cps.view.fxml.FxHelper.getIndexFromComboBox;
import static pl.jkkk.cps.view.fxml.FxHelper.getSelectedTabIndex;
import static pl.jkkk.cps.view.fxml.FxHelper.getValueFromComboBox;
import static pl.jkkk.cps.view.fxml.FxHelper.switchTabToAnother;

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
    private AnchorPane windowTypePane;
    private TextField textFieldCuttingFrequency;
    private TextField textFieldFilterRow;

    private Map<Integer, Signal> signals = new HashMap<>();
    private FileReaderWriter<Signal> signalFileReaderWriter;
    private ReportWriter reportWriter = new ReportWriter();
    private LatexGenerator latexGenerator;
    private boolean isScatterChart;
    private double overallTime = 0;

    private final ADC adc = new ADC();
    private final DAC dac = new DAC();

    /*------------------------ METHODS REGION ------------------------*/
    public Loader(ComboBox comboBoxSignalTypes, ComboBox comboBoxOperationTypesTwoArgs,
                  ComboBox comboBoxFirstSignalTwoArgs, ComboBox comboBoxSecondSignalTwoArgs,
                  TextField textFieldAmplitude, TextField textFieldStartTime,
                  TextField textFieldSignalDuration, TextField textFieldBasicPeriod,
                  TextField textFieldFillFactor, TextField textFieldJumpTime,
                  TextField textFieldProbability, TextField textFieldSamplingFrequency,
                  TabPane tabPaneResults, Spinner spinnerHistogramRange,
                  ComboBox comboBoxOperationTypesOneArgs, ComboBox comboBoxSignalOneArgs,
                  ComboBox comboBoxComparisonFirstSignal, ComboBox comboBoxComparisonSecondSignal,
                  AnchorPane comparisonPane, AnchorPane oneArgsPane,
                  TextField textFieldQuantizationLevels, TextField textFieldSampleRate,
                  TextField textFieldReconstructionSincParam, AnchorPane windowTypePane,
                  TextField textFieldCuttingFrequency, TextField textFieldFilterRow) {
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
        this.windowTypePane = windowTypePane;
        this.textFieldCuttingFrequency = textFieldCuttingFrequency;
        this.textFieldFilterRow = textFieldFilterRow;
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

    /*--------------------------------------------------------------------------------------------*/
    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Collection<ChartRecord<Number, Number>> mainChartData,
                                           Collection<ChartRecord<String, Number>> histogramData,
                                           double[] signalParams) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);

        try {
            clearAndFillBarChart((BarChart) customTabPane.getHistogramTab()
                    .getContent(), histogramData);
            switchTabToAnother(customTabPane, 1);
            reportWriter.writeFxChart("history", Main.getMainArgs(), tabPane);

            if (isScatterChart) {
                changeLineChartToScatterChart(tabPane);
                clearAndFillScatterChart((ScatterChart) customTabPane.getChartTab()
                        .getContent(), mainChartData);
                switchTabToAnother(customTabPane, 0);
                reportWriter.writeFxChart("data", Main.getMainArgs(), tabPane);

            } else {
                changeScatterChartToLineChart(tabPane);
                clearAndFillLineChart((LineChart) customTabPane.getChartTab()
                        .getContent(), mainChartData);
                switchTabToAnother(customTabPane, 0);
                reportWriter.writeFxChart("data", Main.getMainArgs(), tabPane);
            }

            fillParamsTab(customTabPane, signalParams);
            latexGenerator = new LatexGenerator("Signal_Params");
            latexGenerator.createSummaryForSignal(signalParams[0], signalParams[1],
                    signalParams[2], signalParams[3], signalParams[4]);
            latexGenerator.generate(ReportType.SIGNAL);

        } catch (FileOperationException e) {
            e.printStackTrace();
        }
    }

    private void representSignal(Signal signal) {
        /* remember signal */
        int tabIndex = getSelectedTabIndex(tabPaneResults);
        signals.put(tabIndex, signal);

        /* generate discrete representation of signal */
        List<Data> signalData = signal.generateDiscreteRepresentation();

        /* prepare line/point chart data */
        List<Data> data;
        if (signal instanceof GaussianNoise || signal instanceof UniformNoise) {
            data = new ArrayList<>();
            for (int i = 0; i < signalData.size(); i++) {
                if (i % (signalData.size() / 1000) == 0) {
                    data.add(signalData.get(i));
                }
            }
        } else if (signal instanceof ContinuousSignal || signal instanceof OperationResultSignal) {
            DouglasPeuckerAlg douglasPeucker = new DouglasPeuckerAlg();
            data = signalData;
            data = new ArrayList<>(douglasPeucker
                    .calculate(data, (data.get(data.size() - 1).getX() - data.get(0)
                            .getX()) * 1.0 / 10000.0, 0, data.size() - 1));
        } else {
            data = signalData;
        }

        System.out.println("Wygenerowanu punktów: " + data.size());

        List<ChartRecord<Number, Number>> chartData =
                data.stream().map(d -> new ChartRecord<Number, Number>(d.getX(), d.getY()))
                        .collect(Collectors.toList());

        /* prepare barchart data */
        DecimalFormat df = new DecimalFormat("#.##");
        List<ChartRecord<String, Number>> histogramData =
                Signal.generateHistogram((int) spinnerHistogramRange.getValue(), signalData)
                        .stream()
                        .map(range -> new ChartRecord<String, Number>(
                                df.format(range.getBegin()) + " do " + df.format(range.getEnd()),
                                range.getQuantity()))
                        .collect(Collectors.toList());

        /* prepare params */
        double[] signalParams = new double[5];
        signalParams[0] = Signal.meanValue(signalData);
        signalParams[1] = Signal.absMeanValue(signalData);
        signalParams[2] = Signal.rmsValue(signalData);
        signalParams[3] = Signal.varianceValue(signalData);
        signalParams[4] = Signal.meanPowerValue(signalData);

        /* render it all */
        fillCustomTabPaneWithData(tabPaneResults, chartData, histogramData, signalParams);
    }

    /*--------------------------------------------------------------------------------------------*/
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

                signal = new RectangularSignal(rangeStart, rangeLength, amplitude,
                        term, fulfillment);

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
                signal = new UnitImpulseSignal(rangeStart, rangeLength, sampleRate,
                        amplitude, jumpMoment.intValue());

            } else if (selectedSignal.equals(SignalType.IMPULSE_NOISE.getName())) {

                isScatterChart = true;
                signal = new ImpulseNoise(rangeStart, rangeLength, sampleRate, amplitude,
                        probability);

            }

            representSignal(signal);

        } catch (NumberFormatException e) {
            PopOutWindow.messageBox("Błędne Dane", "Wprowadzono błędne dane",
                    Alert.AlertType.WARNING);
        }
    }

    public void performOneArgsOperationOnCharts() {
        Signal signal = null;
        String selectedOperationOneArgs = getValueFromComboBox(comboBoxOperationTypesOneArgs);
        Integer selectedSignalIndex = getIndexFromComboBox(comboBoxSignalOneArgs);
        Signal selectedSignal = signals.get(selectedSignalIndex);

        try {
            long startTime = System.currentTimeMillis();

            if (selectedOperationOneArgs.equals(OneArgsOperationType.SAMPLING.getName())) {
                isScatterChart = true;
                double sampleRate = Double.valueOf(textFieldSampleRate.getText());

                signal = adc.sampling((ContinuousSignal) selectedSignal, sampleRate);

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType.QUANTIZATION.getName())) {
                isScatterChart = true;
                Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
                ComboBox comboBoxMethod = (ComboBox) topPane.getChildren().get(1);
                Integer quantizationLevels = Integer.valueOf(textFieldQuantizationLevels.getText());
                String method = getValueFromComboBox(comboBoxMethod);

                if (method.equals(QuantizationType.EVEN_QUANTIZATION_WITH_TRUNCATION.getName())) {
                    signal = adc.truncatingQuantization((DiscreteSignal) selectedSignal,
                            quantizationLevels);
                } else if (method.equals(QuantizationType.EVEN_QUANTIZATION_WITH_ROUNDING.getName())) {
                    signal = adc.roundingQuantization((DiscreteSignal) selectedSignal,
                            quantizationLevels);
                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType.SIGNAL_RECONSTRUCTION.getName())) {
                isScatterChart = false;
                Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
                ComboBox comboBoxMethod = (ComboBox) topPane.getChildren().get(1);
                String method = getValueFromComboBox(comboBoxMethod);

                if (method.equals(SignalReconstructionType.ZERO_ORDER_EXTRAPOLATION.getName())) {
                    signal = dac.zeroOrderHold((DiscreteSignal) selectedSignal);
                } else if (method.equals(SignalReconstructionType.FIRST_ORDER_INTERPOLATION.getName())) {
                    signal = dac.firstOrderHold((DiscreteSignal) selectedSignal);
                } else if (method.equals(SignalReconstructionType.RECONSTRUCTION_BASED_FUNCTION_SINC
                        .getName())) {
                    Integer sincParam = Integer.valueOf(textFieldReconstructionSincParam.getText());

                    signal = dac.sincBasic((DiscreteSignal) selectedSignal, sincParam);
                }
            }

            overallTime += ((System.currentTimeMillis() - startTime) / 1000.0);

            representSignal(signal);

        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            PopOutWindow.messageBox("Błędne dane", "Wprowadzono błędne dane",
                    Alert.AlertType.WARNING);
        } catch (ClassCastException e) {
            e.printStackTrace();
            PopOutWindow.messageBox("Błędne dane", "Wybrano niepoprawny typ sygnału",
                    Alert.AlertType.WARNING);
        }
    }

    public void performTwoArgsOperationOnCharts() {
        String selectedOperation = getValueFromComboBox(comboBoxOperationTypesTwoArgs);

        int s1Index = getIndexFromComboBox(comboBoxFirstSignalTwoArgs);
        int s2Index = getIndexFromComboBox(comboBoxSecondSignalTwoArgs);

        Signal s1 = signals.get(s1Index);
        Signal s2 = signals.get(s2Index);
        Signal resultSignal = null;

        try {
            if (selectedOperation.equals(TwoArgsOperationType.ADDITION.getName())) {
                resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a + b);
            } else if (selectedOperation.equals(TwoArgsOperationType.SUBTRACTION.getName())) {
                resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a - b);
            } else if (selectedOperation.equals(TwoArgsOperationType.MULTIPLICATION.getName())) {
                resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a * b);
            } else if (selectedOperation.equals(TwoArgsOperationType.DIVISION.getName())) {
                resultSignal = new OperationResultSignal(s1, s2, (a, b) -> a / b);
            } else if (selectedOperation.equals(TwoArgsOperationType.CONVOLUTION.getName())) {
                resultSignal = new ConvolutionSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
            }

            representSignal(resultSignal);

        } catch (ClassCastException e) {
            e.printStackTrace();
            PopOutWindow.messageBox("Błędne dane", "Wybrano niepoprawny typ sygnału",
                    Alert.AlertType.WARNING);
        }
    }

    public void generateComparison() {
        Integer selectedTab1Index = getIndexFromComboBox(comboBoxComparisonFirstSignal);
        Integer selectedTab2Index = getIndexFromComboBox(comboBoxComparisonSecondSignal);
        List<Node> paneChildren = comparisonPane.getChildren();

        List<Data> firstSignalData = signals.get(selectedTab1Index)
                .generateDiscreteRepresentation();
        List<Data> secondSignalData = signals.get(selectedTab2Index)
                .generateDiscreteRepresentation();
        DecimalFormat df = new DecimalFormat("##.####");

        try {
            double meanSquaredError = Signal.meanSquaredError(secondSignalData, firstSignalData);
            double signalToNoiseRatio = Signal.signalToNoiseRatio(secondSignalData,
                    firstSignalData);
            double peakSignalToNoiseRatio = Signal.peakSignalToNoiseRatio(secondSignalData,
                    firstSignalData);
            double maximumDifference = Signal.maximumDifference(secondSignalData, firstSignalData);
            double effectiveNumberOfBits = Signal.effectiveNumberOfBits(secondSignalData,
                    firstSignalData);

            appendLabelText(paneChildren.get(0), "" + df.format(meanSquaredError));
            appendLabelText(paneChildren.get(1), "" + df.format(signalToNoiseRatio));
            appendLabelText(paneChildren.get(2), "" + df.format(peakSignalToNoiseRatio));
            appendLabelText(paneChildren.get(3), "" + df.format(maximumDifference));
            appendLabelText(paneChildren.get(4), "" + df.format(effectiveNumberOfBits));
            appendLabelText(paneChildren.get(5), "" + df.format(overallTime));

            latexGenerator = new LatexGenerator("Comparison");
            latexGenerator.createSummaryForComparison(meanSquaredError, signalToNoiseRatio,
                    peakSignalToNoiseRatio, maximumDifference, effectiveNumberOfBits, overallTime);
            latexGenerator.generate(ReportType.COMPARISON);

        } catch (NotSameLengthException e) {
            PopOutWindow.messageBox("Błednie wybrane wykresy",
                    "Wykresy mają błędnie dobraną długość",
                    Alert.AlertType.WARNING);
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    public void loadChart() {
        int tabIndex = getSelectedTabIndex(tabPaneResults);

        try {
            signalFileReaderWriter = new FileReaderWriter<>(
                    new FileChooser().showOpenDialog(StageController.getApplicationStage())
                            .getName());
            representSignal(signalFileReaderWriter.read());

        } catch (NullPointerException | FileOperationException e) {
            PopOutWindow
                    .messageBox("Błąd Ładowania Pliku",
                            "Nie można załadować wybranego pliku",
                            Alert.AlertType.WARNING);
        }
    }

    public void saveChart() {
        int tabIndex = getSelectedTabIndex(tabPaneResults);

        try {
            if (signals.get(tabIndex) != null) {
                signalFileReaderWriter = new FileReaderWriter<>(
                        new FileChooser().showSaveDialog(StageController.getApplicationStage())
                                .getName());
                signalFileReaderWriter.write(signals.get(tabIndex));
            } else {
                PopOutWindow.messageBox("Błąd Zapisu Do Pliku",
                        "Sygnał nie został jeszcze wygenerowany",
                        Alert.AlertType.WARNING);
            }
        } catch (NullPointerException | FileOperationException e) {
            PopOutWindow.messageBox("Błąd Zapisu Do Pliku",
                    "Nie można zapisać do wybranego pliku",
                    Alert.AlertType.WARNING);
        }
    }
}
