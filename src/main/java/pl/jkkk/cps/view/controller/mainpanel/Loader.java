package pl.jkkk.cps.view.controller.mainpanel;

import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
import pl.jkkk.cps.view.helper.CustomTabPane;
import pl.jkkk.cps.view.util.PopOutWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jkkk.cps.view.helper.ChartHelper.appendLabelText;
import static pl.jkkk.cps.view.helper.ChartHelper.castTabPaneToCustomTabPane;
import static pl.jkkk.cps.view.helper.ChartHelper.changeLineChartToScatterChart;
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

    private Series chartData;
    private List<ChartRecord<String, Number>> histogramData;
    private boolean isScatterChart;

    /*------------------------ METHODS REGION ------------------------*/
    public Loader(ComboBox comboBoxSignalTypes, ComboBox comboBoxOperationTypes,
                  ComboBox comboBoxFirstSignal, ComboBox comboBoxSecondSignal,
                  TextField textFieldAmplitude, TextField textFieldStartTime,
                  TextField textFieldSignalDuration, TextField textFieldBasicPeriod,
                  TextField textFieldFillFactor, TextField textFieldJumpTime,
                  TextField textFieldProbability, TextField textFieldSamplingFrequency,
                  TabPane tabPaneResults, Spinner spinnerHistogramRange, Series chartData,
                  List<ChartRecord<String, Number>> histogramData, boolean isScatterChart) {
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
        this.chartData = chartData;
        this.histogramData = histogramData;
        this.isScatterChart = isScatterChart;
    }

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

    public void changeHistogramRange() {
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

    public void computeCharts() {
        String selectedSignal = comboBoxSignalTypes.getSelectionModel()
                .getSelectedItem().toString();

        Double amplitude = null;
        Double rangeStart = null;
        Double rangeLength = null;
        Double term = null;
        Double fulfillment = null;
        Double jumpMoment = null;
        Double probability = null;
        Double sampleRate = null;

        try {
            amplitude = Double.parseDouble(textFieldAmplitude.getText());
            rangeStart = Double.parseDouble(textFieldStartTime.getText());
            rangeLength = Double.parseDouble(textFieldSignalDuration.getText());
            term = Double.parseDouble(textFieldBasicPeriod.getText());
            fulfillment = Double.parseDouble(textFieldFillFactor.getText());
            jumpMoment = Double.parseDouble(textFieldJumpTime.getText());
            probability = Double.parseDouble(textFieldProbability.getText());
            sampleRate = Double.parseDouble(textFieldSamplingFrequency.getText());

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

                signal = new UnitImpulseSignal(rangeStart, rangeLength, sampleRate,
                        amplitude, jumpMoment.intValue());

            } else if (selectedSignal.equals(SignalType.IMPULSE_NOISE.getName())) {

                isScatterChart = true;
                changeLineChartToScatterChart(tabPaneResults,
                        new ScatterChart(new NumberAxis(), new NumberAxis()));

                signal = new ImpulseNoise(rangeStart, rangeLength, sampleRate, amplitude,
                        probability);

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
        } catch (NumberFormatException e) {
            PopOutWindow.messageBox("Błędne Dane",
                    "Wprowadzono błędne dane", Alert.AlertType.WARNING);

        }
    }

    public void performOperationOnCharts() {
        String selectedOperation = comboBoxOperationTypes.getSelectionModel()
                .getSelectedItem().toString();

    }
}
    