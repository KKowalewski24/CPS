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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.model.data.Data;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.GaussianNoise;
import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.model.signal.UniformNoise;
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

import static pl.jkkk.cps.view.constant.Constants.PATH_CSS_LIGHT_STYLING;
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

    private ReportWriter reportWriter = new ReportWriter();
    private LatexGenerator latexGenerator;

    private StackPane root;
    private TabPane tabPane;
    private LineChart lineChart;
    private ScatterChart scatterChart;
    private BarChart barChart;

    /*------------------------ METHODS REGION ------------------------*/
    public void main() throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        commandLineStage = stage;

        OperationCmd operationCmd = OperationCmd.fromString(Main.getMainArgs().get(0));
        switch (operationCmd) {
            case GENERATE: {
                caseGenerate();
                break;
            }
            case SAMPLING: {
                OneArgsOperationProcessor.caseSampling();
                break;
            }
            case QUANTIZATION: {
                OneArgsOperationProcessor.caseQuantization();
                break;
            }
            case RECONSTRUCTION: {
                OneArgsOperationProcessor.caseReconstruction();
                break;
            }
            case DISCRETE_FOURIER_TRANSFORMATION: {
                OneArgsOperationProcessor.caseDiscreteFourierTransformation();
                break;
            }
            case COSINE_TRANSFORMATION: {
                OneArgsOperationProcessor.caseCosineTransformation();
                break;
            }
            case WALSH_HADAMARD_TRANSFORMATION: {
                OneArgsOperationProcessor.caseWalshHadamardTransformation();
                break;
            }
            case WAVELET_TRANSFORMATION: {
                OneArgsOperationProcessor.caseWaveletTransformation();
                break;
            }
            case COMPARISON: {
                caseComparison();
                break;
            }
            case DRAW_CHARTS: {
                caseDrawCharts();
                break;
            }
            case ADD: {
                TwoArgOperationProcessor.add();
                break;
            }
            case SUBTRACT: {
                TwoArgOperationProcessor.subtract();
                break;
            }
            case MULTIPLY: {
                TwoArgOperationProcessor.multiply();
                break;
            }
            case DIVIDE: {
                TwoArgOperationProcessor.divide();
                break;
            }
            case CONVOLUTION: {
                TwoArgOperationProcessor.convolution();
                break;
            }
            case CORRELATION: {
                TwoArgOperationProcessor.correlation();
                break;
            }
        }
        System.exit(0);
    }

    public static Signal readSignal(String filename) throws FileOperationException {
        return new FileReaderWriter<Signal>(filename).read();
    }

    public static void writeSignal(Signal signal, String filename) throws FileOperationException {
        new FileReaderWriter<>(filename).write(signal);
    }

    public void caseGenerate() throws FileOperationException {
        Signal signal = SignalGenerator.generate(Main.getMainArgs()
                .stream()
                .toArray(String[]::new));
        writeSignal(signal, Main.getMainArgs().get(1));

        latexGenerator = new LatexGenerator("Input_gene");
        latexGenerator.createSummaryForInputParameters(Main.getMainArgs(), 2);
        latexGenerator.generate(ReportType.INPUT_PARAMETERS);
    }

    private void caseComparison() throws FileOperationException {
        List<Data> firstSignalData = readSignal(Main.getMainArgs().get(1))
                .generateDiscreteRepresentation();
        List<Data> secondSignalData = readSignal(Main.getMainArgs().get(2))
                .generateDiscreteRepresentation();

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
    }

    private void caseDrawCharts() throws FileOperationException {
        root = new StackPane();
        tabPane = new TabPane();
        lineChart = prepareLineChart();
        scatterChart = prepareScatterChart();
        barChart = prepareBarChart();

        tabPane.getTabs().add(new Tab("Karta ",
                new CustomTabPane(
                        new CustomTab("Wykres", lineChart, false),
                        new CustomTab("Histogram", barChart, false),
                        new CustomTab("Parametry", new Pane(), false),
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

        root.getChildren().addAll(tabPane);
        Scene scene = new Scene(root, 700, 600);
        scene.getStylesheets().add(PATH_CSS_LIGHT_STYLING);
        commandLineStage.setScene(scene);
        commandLineStage.show();

        for (int i = 1; i < Main.getMainArgs().size(); i++) {
            drawChart(readSignal(Main.getMainArgs().get(i)));
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
        } else if (signal instanceof ContinuousSignal) {
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

        //        TODO COPY GENERATING W1 AND W2 CHARTS FROM LOADER

        latexGenerator = new LatexGenerator("Signal_Params");
        latexGenerator.createSummaryForSignal(signalParams[0], signalParams[1],
                signalParams[2], signalParams[3], signalParams[4]);
        latexGenerator.generate(ReportType.SIGNAL);
    }
}
