package pl.jkkk.cps.executionmode.commandline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.model.ADC;
import pl.jkkk.cps.logic.model.DAC;
import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
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
import pl.jkkk.cps.view.model.ChartRecord;
import pl.jkkk.cps.view.model.CustomTab;
import pl.jkkk.cps.view.model.CustomTabPane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static pl.jkkk.cps.view.fxml.FxHelper.changeLineChartToScatterChart;
import static pl.jkkk.cps.view.fxml.FxHelper.changeScatterChartToLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.fillBarChart;
import static pl.jkkk.cps.view.fxml.FxHelper.fillLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.fillScatterChart;
import static pl.jkkk.cps.view.fxml.FxHelper.getCurrentCustomTabPaneFromTabPane;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareBarChart;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.prepareScatterChart;
import static pl.jkkk.cps.view.fxml.FxHelper.switchTabToAnother;

public class CommandLineMode extends Application {

    /*------------------------ FIELDS REGION ------------------------*/
    private static Stage commandLineStage;

    private final ADC adc = new ADC();
    private final DAC dac = new DAC();
    private ReportWriter reportWriter = new ReportWriter();
    private LatexGenerator latexGenerator;

    private StackPane root;
    private TabPane tabPane;
    private LineChart lineChart;
    private ScatterChart scatterChart;
    private BarChart barChart;

    /*------------------------ METHODS REGION ------------------------*/
    private Signal generate(String[] args) {
        switch (SignalType.fromString(args[2])) {
            case UNIFORM_NOISE: {
                return new UniformNoise(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]));
            }
            case GAUSSIAN_NOISE: {
                return new GaussianNoise(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]));
            }
            case SINUSOIDAL_SIGNAL: {
                return new SinusoidalSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            }
            case SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL: {
                return new SinusoidalRectifiedOneHalfSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            }
            case SINUSOIDAL_RECTIFIED_IN_TWO_HALVES: {
                return new SinusoidalRectifiedTwoHalfSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            }
            case RECTANGULAR_SIGNAL: {
                return new RectangularSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]));
            }
            case SYMMETRICAL_RECTANGULAR_SIGNAL: {
                return new RectangularSymmetricSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]));
            }
            case TRIANGULAR_SIGNAL: {
                return new TriangularSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]));
            }
            case UNIT_JUMP: {
                return new UnitJumpSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            }
            case UNIT_IMPULSE: {
                return new UnitImpulseSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Integer.parseInt(args[7]));
            }
            case IMPULSE_NOISE: {
                return new ImpulseNoise(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]));
            }
        }

        return null;
    }

    private void fillCustomTabPaneWithData(TabPane tabPane,
                                           Collection<ChartRecord<Number, Number>> mainChartData,
                                           Collection<ChartRecord<String, Number>> histogramData,
                                           double[] signalParams,
                                           boolean isScatterChart) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);

        fillBarChart((BarChart) customTabPane.getHistogramTab()
                .getContent(), histogramData);

        if (isScatterChart) {
            changeLineChartToScatterChart(tabPane, scatterChart);
            fillScatterChart((ScatterChart) customTabPane.getChartTab()
                    .getContent(), mainChartData);

        } else {
            changeScatterChartToLineChart(tabPane, lineChart);
            fillLineChart((LineChart) customTabPane.getChartTab()
                    .getContent(), mainChartData);
        }

        latexGenerator = new LatexGenerator("Signal_Params");
        latexGenerator.createSummaryForSignal(signalParams[0], signalParams[1],
                signalParams[2], signalParams[3], signalParams[4]);
        latexGenerator.generate(ReportType.SIGNAL);
    }

    private void drawChart(Signal signal) {
        List<Data> signalData = signal.generateDiscreteRepresentation();

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

        List<ChartRecord<Number, Number>> chartData = data
                .stream()
                .map(d -> new ChartRecord<Number, Number>(d.getX(), d.getY()))
                .collect(Collectors.toList());

        DecimalFormat df = new DecimalFormat("#.##");
        List<ChartRecord<String, Number>> histogramData = Signal
                .generateHistogram(10, signalData)
                .stream()
                .map(range -> new ChartRecord<String, Number>(
                        df.format(range.getBegin()) + " do " + df.format(range.getEnd()),
                        range.getQuantity()))
                .collect(Collectors.toList());

        double[] signalParams = new double[5];
        signalParams[0] = Signal.meanValue(signalData);
        signalParams[1] = Signal.absMeanValue(signalData);
        signalParams[2] = Signal.rmsValue(signalData);
        signalParams[3] = Signal.varianceValue(signalData);
        signalParams[4] = Signal.meanPowerValue(signalData);

        fillCustomTabPaneWithData(tabPane, chartData, histogramData, signalParams, 
                signal instanceof DiscreteSignal);
    }

    @Override
    public void start(Stage stage) throws Exception {
        commandLineStage = stage;

        Operation operation = Operation.fromString(Main.getMainArgs().get(0));
        Signal signal;
        FileReaderWriter<Signal> readerWriter;
        switch (operation) {
            case GENERATE: {
                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(1));
                signal = generate(Main.getMainArgs().stream().toArray(String[]::new));
                readerWriter.write(signal);
                break;
            }
            case SAMPLING: {
                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(1));
                signal = readerWriter.read();
                signal = adc.sampling((ContinuousSignal) signal,
                        Double.valueOf(Main.getMainArgs().get(3)));
                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(2));
                readerWriter.write(signal);
                break;
            }
            case QUANTIZATION: {
                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(1));
                signal = readerWriter.read();

                if (Operation.EVEN_QUANTIZATION_WITH_TRUNCATION
                        == Operation.fromString(Main.getMainArgs().get(3))) {

                    signal = adc.truncatingQuantization((DiscreteSignal) signal,
                            Integer.valueOf(Main.getMainArgs().get(4)));

                } else if (Operation.EVEN_QUANTIZATION_WITH_ROUNDING
                        == Operation.fromString(Main.getMainArgs().get(3))) {

                    signal = adc.roundingQuantization((DiscreteSignal) signal,
                            Integer.valueOf(Main.getMainArgs().get(4)));

                }

                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(2));
                readerWriter.write(signal);
                break;
            }
            case RECONSTRUCTION: {
                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(1));
                signal = readerWriter.read();

                if (Operation.ZERO_ORDER_EXTRAPOLATION
                        == Operation.fromString(Main.getMainArgs().get(3))) {
                    signal = dac.zeroOrderHold((DiscreteSignal) signal);

                } else if (Operation.FIRST_ORDER_INTERPOLATION
                        == Operation.fromString(Main.getMainArgs().get(3))) {
                    signal = dac.firstOrderHold((DiscreteSignal) signal);

                } else if (Operation.RECONSTRUCTION_BASED_FUNCTION_SINC
                        == Operation.fromString(Main.getMainArgs().get(3))) {

                    signal = dac.sincBasic((DiscreteSignal) signal,
                            Integer.valueOf(Main.getMainArgs().get(4)));
                }

                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(2));
                readerWriter.write(signal);
                break;
            }
            case COMPARISON: {
                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(1));
                List<Data> firstSignalData = readerWriter.read().generateDiscreteRepresentation();
                readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(2));
                List<Data> secondSignalData = readerWriter.read().generateDiscreteRepresentation();

                double meanSquaredError = Signal.meanSquaredError(secondSignalData,
                        firstSignalData);
                double signalToNoiseRatio = Signal.signalToNoiseRatio(secondSignalData,
                        firstSignalData);
                double peakSignalToNoiseRatio = Signal.peakSignalToNoiseRatio(secondSignalData,
                        firstSignalData);
                double maximumDifference = Signal.maximumDifference(secondSignalData,
                        firstSignalData);
                double effectiveNumberOfBits = Signal.effectiveNumberOfBits(secondSignalData,
                        firstSignalData);

                latexGenerator = new LatexGenerator("Comparison");
                latexGenerator.createSummaryForComparison(meanSquaredError,
                        signalToNoiseRatio, peakSignalToNoiseRatio,
                        maximumDifference, effectiveNumberOfBits,
                        /*TODO ADD overall time*/0);
                latexGenerator.generate(ReportType.COMPARISON);
                break;
            }
            case DRAW_CHARTS: {
                root = new StackPane();
                tabPane = new TabPane();
                lineChart = prepareLineChart();
                scatterChart = prepareScatterChart();
                barChart = prepareBarChart();

                tabPane.getTabs().add(new Tab("Karta ",
                        new CustomTabPane(
                                new CustomTab("Wykres", lineChart, false),
                                new CustomTab("Histogram", barChart, false),
                                new CustomTab("Parametry", new Pane(), false)
                        )));

                root.getChildren().addAll(tabPane);
                commandLineStage.setScene(new Scene(root, 700, 600));
                commandLineStage.show();

                for (int i = 1; i < Main.getMainArgs().size(); i++) {
                    readerWriter = new FileReaderWriter<>(Main.getMainArgs().get(i));
                    Signal signalInLoop = readerWriter.read();
                    drawChart(signalInLoop);
                }

                try {
                    CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);
                    switchTabToAnother(customTabPane, 1);
                    reportWriter.writeFxChart("histogram", Main.getMainArgs(), tabPane);
                    switchTabToAnother(customTabPane, 0);
                    reportWriter.writeFxChart("data", Main.getMainArgs(), tabPane);
                } catch (FileOperationException e) {
                    e.printStackTrace();
                }

                break;
            }
        }
        System.exit(0);
    }

    public void main() throws Exception {
        launch();
    }
}
