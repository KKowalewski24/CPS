package pl.jkkk.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.exception.NotSameLengthException;
import pl.jkkk.cps.logic.model.ADC;
import pl.jkkk.cps.logic.model.DAC;
import pl.jkkk.cps.logic.model.data.ComplexData;
import pl.jkkk.cps.logic.model.data.Data;
import pl.jkkk.cps.logic.model.enumtype.AlgorithmType;
import pl.jkkk.cps.logic.model.enumtype.OneArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.QuantizationType;
import pl.jkkk.cps.logic.model.enumtype.SignalReconstructionType;
import pl.jkkk.cps.logic.model.enumtype.SignalType;
import pl.jkkk.cps.logic.model.enumtype.TwoArgsOperationType;
import pl.jkkk.cps.logic.model.enumtype.WaveletType;
import pl.jkkk.cps.logic.model.enumtype.WindowType;
import pl.jkkk.cps.logic.model.signal.BandPassFilter;
import pl.jkkk.cps.logic.model.signal.ComplexDiscreteSignal;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.ConvolutionSignal;
import pl.jkkk.cps.logic.model.signal.CorrelationSignal;
import pl.jkkk.cps.logic.model.signal.DFTSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.FastDFTSignal;
import pl.jkkk.cps.logic.model.signal.GaussianNoise;
import pl.jkkk.cps.logic.model.signal.HighPassFilter;
import pl.jkkk.cps.logic.model.signal.ImpulseNoise;
import pl.jkkk.cps.logic.model.signal.LowPassFilter;
import pl.jkkk.cps.logic.model.signal.OperationResultContinuousSignal;
import pl.jkkk.cps.logic.model.signal.OperationResultDiscreteSignal;
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
import pl.jkkk.cps.logic.model.Operation;
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
import static pl.jkkk.cps.view.fxml.FxHelper.isCheckBoxSelected;
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

    private CheckBox checkBoxDataChart;
    private CheckBox checkBoxHistogram;
    private CheckBox checkBoxSignalParams;
    private CheckBox checkBoxComparison;
    private CheckBox checkBoxTransformation;

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
                  TextField textFieldCuttingFrequency, TextField textFieldFilterRow,
                  CheckBox checkBoxDataChart, CheckBox checkBoxHistogram,
                  CheckBox checkBoxSignalParams, CheckBox checkBoxComparison,
                  CheckBox checkBoxTransformation) {
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
        this.checkBoxDataChart = checkBoxDataChart;
        this.checkBoxHistogram = checkBoxHistogram;
        this.checkBoxSignalParams = checkBoxSignalParams;
        this.checkBoxComparison = checkBoxComparison;
        this.checkBoxTransformation = checkBoxTransformation;
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
            Double cuttingFrequency = Double.parseDouble(textFieldCuttingFrequency.getText());
            Integer filterRow = Integer.parseInt(textFieldFilterRow.getText());

            WindowType selectedWindowType = null;
            if (selectedSignal.equals(SignalType.LOW_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.BAND_PASS_FILTER.getName())
                    || selectedSignal.equals(SignalType.HIGH_PASS_FILTER.getName())) {
                selectedWindowType = WindowType.fromString(
                        getValueFromComboBox((ComboBox) windowTypePane.getChildren().get(1)));
            }
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
                signal = new UnitImpulseSignal(rangeStart, rangeLength, sampleRate,
                        amplitude, jumpMoment.intValue());
            } else if (selectedSignal.equals(SignalType.IMPULSE_NOISE.getName())) {
                signal = new ImpulseNoise(rangeStart, rangeLength, sampleRate, amplitude,
                        probability);
            } else if (selectedSignal.equals(SignalType.LOW_PASS_FILTER.getName())) {
                signal = new LowPassFilter(sampleRate, filterRow, cuttingFrequency,
                        WindowType.fromEnum(selectedWindowType, filterRow));
            } else if (selectedSignal.equals(SignalType.BAND_PASS_FILTER.getName())) {
                signal = new BandPassFilter(sampleRate, filterRow, cuttingFrequency,
                        WindowType.fromEnum(selectedWindowType, filterRow));
            } else if (selectedSignal.equals(SignalType.HIGH_PASS_FILTER.getName())) {
                signal = new HighPassFilter(sampleRate, filterRow, cuttingFrequency,
                        WindowType.fromEnum(selectedWindowType, filterRow));
            }

            representSignal(signal);

        } catch (IllegalArgumentException e) {
            PopOutWindow.messageBox("Błędne Dane", "Wprowadzono błędne dane",
                    Alert.AlertType.WARNING);
        }
    }

    public void performOneArgsOperationOnCharts() {
        Signal signal = null;
        String selectedOperationOneArgs = getValueFromComboBox(comboBoxOperationTypesOneArgs);
        Integer selectedSignalIndex = getIndexFromComboBox(comboBoxSignalOneArgs);
        Signal selectedSignal = signals.get(selectedSignalIndex);

        final Pane topPane = (Pane) oneArgsPane.getChildren().get(0);
        final Pane middlePane = (Pane) oneArgsPane.getChildren().get(1);
        final ComboBox comboBoxMethodOrAlgorithm = (ComboBox) topPane.getChildren().get(1);
        final TextField textFieldComputationTime = (TextField) middlePane.getChildren().get(1);
        //        TODO ADD SETTING VALUE OF COMPUTATIONTIME

        try {
            long startTime = System.currentTimeMillis();

            if (selectedOperationOneArgs.equals(OneArgsOperationType.SAMPLING.getName())) {
                double sampleRate = Double.valueOf(textFieldSampleRate.getText());
                signal = adc.sampling((ContinuousSignal) selectedSignal, sampleRate);

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType.QUANTIZATION.getName())) {
                final Integer quantizationLevels = Integer
                        .valueOf(textFieldQuantizationLevels.getText());
                final String method = getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (method.equals(QuantizationType.EVEN_QUANTIZATION_WITH_TRUNCATION.getName())) {
                    signal = adc.truncatingQuantization((DiscreteSignal) selectedSignal,
                            quantizationLevels);
                } else if (method.equals(QuantizationType.EVEN_QUANTIZATION_WITH_ROUNDING.getName())) {
                    signal = adc.roundingQuantization((DiscreteSignal) selectedSignal,
                            quantizationLevels);
                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType.SIGNAL_RECONSTRUCTION.getName())) {
                final String method = getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (method.equals(SignalReconstructionType.ZERO_ORDER_EXTRAPOLATION.getName())) {
                    signal = dac.zeroOrderHold((DiscreteSignal) selectedSignal);
                } else if (method.equals(SignalReconstructionType.FIRST_ORDER_INTERPOLATION.getName())) {
                    signal = dac.firstOrderHold((DiscreteSignal) selectedSignal);
                } else if (method.equals(SignalReconstructionType.RECONSTRUCTION_BASED_FUNCTION_SINC
                        .getName())) {
                    Integer sincParam = Integer.valueOf(textFieldReconstructionSincParam.getText());
                    signal = dac.sincBasic((DiscreteSignal) selectedSignal, sincParam);

                }
            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .DISCRETE_FOURIER_TRANSFORMATION.getName())) {
                final String algorithm = getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (algorithm.equals(AlgorithmType.BY_DEFINITION.getName())) {
                    signal = new DFTSignal((DiscreteSignal) selectedSignal);
                } else if (algorithm.equals(AlgorithmType.FAST_TRANSFORMATION.getName())) {
                    signal = new FastDFTSignal((DiscreteSignal) selectedSignal);
                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .INVERSE_DISCRETE_FOURIER_TRANSFORMATION.getName())) {
                final String algorithm = getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (algorithm.equals(AlgorithmType.BY_DEFINITION.getName())) {

                } else if (algorithm.equals(AlgorithmType.FAST_TRANSFORMATION.getName())) {

                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .COSINE_TRANSFORMATION.getName())) {
                final String algorithm = getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (algorithm.equals(AlgorithmType.BY_DEFINITION.getName())) {

                } else if (algorithm.equals(AlgorithmType.FAST_TRANSFORMATION.getName())) {

                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .WALSH_HADAMARD_TRANSFORMATION.getName())) {
                final String algorithm = getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (algorithm.equals(AlgorithmType.BY_DEFINITION.getName())) {

                } else if (algorithm.equals(AlgorithmType.FAST_TRANSFORMATION.getName())) {

                }

            } else if (selectedOperationOneArgs.equals(OneArgsOperationType
                    .WAVELET_TRANSFORMATION.getName())) {
                final String level = getValueFromComboBox(comboBoxMethodOrAlgorithm);

                if (level.equals(WaveletType.DB4.getName())) {

                } else if (level.equals(WaveletType.DB6.getName())) {

                } else if (level.equals(WaveletType.DB8.getName())) {

                }
            }

            overallTime += ((System.currentTimeMillis() - startTime) / 1000.0);

            if (signal instanceof ComplexDiscreteSignal) {
                representComplexSignal(signal);
            } else {
                representSignal(signal);
            }

        } catch (NullPointerException | NumberFormatException e) {
            PopOutWindow.messageBox("Błędne dane", "Wprowadzono błędne dane",
                    Alert.AlertType.WARNING);
        } catch (ClassCastException e) {
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
            if (selectedOperation.equals(TwoArgsOperationType.CONVOLUTION.getName())) {
                resultSignal = new ConvolutionSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
            } else if (selectedOperation.equals(TwoArgsOperationType.CORRELATION.getName())) {
                resultSignal = new CorrelationSignal((DiscreteSignal) s1, (DiscreteSignal) s2);
            } else {
                Operation operation;
                if (selectedOperation.equals(TwoArgsOperationType.ADDITION.getName())) {
                    operation = (a, b) -> a + b;
                } else if (selectedOperation.equals(TwoArgsOperationType.SUBTRACTION.getName())) {
                    operation = (a, b) -> a - b;
                } else if (selectedOperation.equals(TwoArgsOperationType.MULTIPLICATION.getName())) {
                    operation = (a, b) -> a * b;
                } else {
                    operation = (a, b) -> a / b;
                }
                if (s1 instanceof DiscreteSignal) {
                    resultSignal = new OperationResultDiscreteSignal(
                            (DiscreteSignal) s1,
                            (DiscreteSignal) s2,
                            operation);
                } else {
                    resultSignal = new OperationResultContinuousSignal(
                            (ContinuousSignal) s1,
                            (ContinuousSignal) s2,
                            operation);
                }
            }

            representSignal(resultSignal);

        } catch (NotSameLengthException e) {
            PopOutWindow.messageBox("Błednie wybrane wykresy",
                    "Wykresy mają błędnie dobraną długość",
                    Alert.AlertType.WARNING);
        } catch (ClassCastException e) {
            PopOutWindow.messageBox("Błędne dane",
                    "Wybrano niepoprawny typ sygnału",
                    Alert.AlertType.WARNING);
        } catch (NullPointerException e) {
            PopOutWindow.messageBox("Brak Wygenerowanego Sygnału",
                    "Sygnał nie został jeszcze wygenerowany",
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

            if (isCheckBoxSelected(checkBoxComparison)) {
                latexGenerator = new LatexGenerator("Comparison");
                latexGenerator.createSummaryForComparison(meanSquaredError, signalToNoiseRatio,
                        peakSignalToNoiseRatio, maximumDifference, effectiveNumberOfBits,
                        overallTime);
                latexGenerator.generate(ReportType.COMPARISON);
            }

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

    /*--------------------------------------------------------------------------------------------*/
    private void representComplexSignal(Signal signal) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPaneResults);

        List<ComplexData> signalComplexData = ((ComplexDiscreteSignal) signal)
                .generateComplexDiscreteRepresentation();

        List<ChartRecord<Number, Number>> chartDataReal = signalComplexData
                .stream()
                .map((it) -> new ChartRecord<Number, Number>(it.getX(), it.getY().getReal()))
                .collect(Collectors.toList());

        List<ChartRecord<Number, Number>> chartDataImaginary = signalComplexData
                .stream()
                .map((it) -> new ChartRecord<Number, Number>(it.getX(), it.getY().getImaginary()))
                .collect(Collectors.toList());

        List<ChartRecord<Number, Number>> chartDataAbs = signalComplexData
                .stream()
                .map((it) -> new ChartRecord<Number, Number>(it.getX(), it.getY().abs()))
                .collect(Collectors.toList());

        List<ChartRecord<Number, Number>> chartDataArgument = signalComplexData
                .stream()
                .map((it) -> new ChartRecord<Number, Number>(it.getX(), it.getY().getArgument()))
                .collect(Collectors.toList());

        try {
            VBox vBoxW1 = (VBox) customTabPane.getTabW1().getContent();
            clearAndFillLineChart((LineChart) vBoxW1.getChildren().get(0), chartDataReal);
            clearAndFillLineChart((LineChart) vBoxW1.getChildren().get(1), chartDataImaginary);

            VBox vBoxW2 = (VBox) customTabPane.getTabW2().getContent();
            clearAndFillLineChart((LineChart) vBoxW2.getChildren().get(0), chartDataAbs);
            clearAndFillLineChart((LineChart) vBoxW2.getChildren().get(1), chartDataArgument);

            if (isCheckBoxSelected(checkBoxTransformation)) {
                switchTabToAnother(customTabPane, 3);
                reportWriter.writeFxChart("W1", Main.getMainArgs(), tabPaneResults);

                switchTabToAnother(customTabPane, 4);
                reportWriter.writeFxChart("W2", Main.getMainArgs(), tabPaneResults);
            }

            switchTabToAnother(customTabPane, 3);
        } catch (FileOperationException e) {
            PopOutWindow.messageBox("Błąd Zapisu Do Pliku",
                    "Nie można zapisać raportu do pliku",
                    Alert.AlertType.WARNING);
        }
    }

    /*--------------------------------------------------------------------------------------------*/
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
        } else if (signal instanceof ContinuousSignal) {
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
        fillCustomTabPaneWithData(tabPaneResults, chartData, histogramData, signalParams,
                signal instanceof DiscreteSignal);
    }

    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Collection<ChartRecord<Number, Number>> mainChartData,
                                           Collection<ChartRecord<String, Number>> histogramData,
                                           double[] signalParams, boolean isScatterChart) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);

        try {
            clearAndFillBarChart((BarChart) customTabPane.getHistogramTab()
                    .getContent(), histogramData);
            switchTabToAnother(customTabPane, 1);

            if (isCheckBoxSelected(checkBoxHistogram)) {
                reportWriter.writeFxChart("history", Main.getMainArgs(), tabPane);
            }

            if (isScatterChart) {
                changeLineChartToScatterChart(tabPane);
                clearAndFillScatterChart((ScatterChart) customTabPane.getChartTab()
                        .getContent(), mainChartData);
                switchTabToAnother(customTabPane, 0);

                if (isCheckBoxSelected(checkBoxDataChart)) {
                    reportWriter.writeFxChart("data", Main.getMainArgs(), tabPane);
                }

            } else {
                changeScatterChartToLineChart(tabPane);
                clearAndFillLineChart((LineChart) customTabPane.getChartTab()
                        .getContent(), mainChartData);
                switchTabToAnother(customTabPane, 0);

                if (isCheckBoxSelected(checkBoxDataChart)) {
                    reportWriter.writeFxChart("data", Main.getMainArgs(), tabPane);
                }
            }

            fillParamsTab(customTabPane, signalParams);

            if (isCheckBoxSelected(checkBoxSignalParams)) {
                latexGenerator = new LatexGenerator("Signal_Params");
                latexGenerator.createSummaryForSignal(signalParams[0], signalParams[1],
                        signalParams[2], signalParams[3], signalParams[4]);
                latexGenerator.generate(ReportType.SIGNAL);
            }

        } catch (FileOperationException e) {
            PopOutWindow.messageBox("Błąd Zapisu Do Pliku",
                    "Nie można zapisać raportu do pliku",
                    Alert.AlertType.WARNING);
        }
    }

    /*--------------------------------------------------------------------------------------------*/
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
}
