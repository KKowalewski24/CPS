package pl.jkkk.cps.view.controller.animationpanel;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.GaussianNoise;
import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.model.signal.UniformNoise;
import pl.jkkk.cps.logic.model.simulator.DistanceSensor;
import pl.jkkk.cps.logic.model.simulator.Environment;
import pl.jkkk.cps.view.fxml.DouglasPeuckerAlg;
import pl.jkkk.cps.view.model.ChartRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static pl.jkkk.cps.view.fxml.FxHelper.clearAndFillLineChart;
import static pl.jkkk.cps.view.fxml.FxHelper.textFieldSetValue;
import static pl.jkkk.cps.view.fxml.FxHelper.updateNumberAxis;

public class AnimationThread {

    /*------------------------ FIELDS REGION ------------------------*/
    public static final double AXIS_TICK_UNIT = 0.25;

    private AtomicBoolean isAnimationRunning = new AtomicBoolean(false);
    private AtomicBoolean isAnimationPaused = new AtomicBoolean(false);

    private Thread thread;

    /*------------------------ METHODS REGION ------------------------*/
    public AtomicBoolean getIsAnimationRunning() {
        return isAnimationRunning;
    }

    public AtomicBoolean getIsAnimationPaused() {
        return isAnimationPaused;
    }

    public void startAnimation(Environment environment,
                               LineChart chartSignalProbe,
                               LineChart chartSignalFeedback,
                               LineChart chartCorrelation,
                               NumberAxis axisXSignalProbe,
                               NumberAxis axisXSignalFeedback,
                               NumberAxis lineChartSignalCorrelation,
                               TextField textFieldResultTimeStamp,
                               TextField textFieldResultRealDistance,
                               TextField textFieldResultCalculatedDistance) {
        isAnimationRunning.set(true);
        run(environment, chartSignalProbe, chartSignalFeedback,
                chartCorrelation, axisXSignalProbe, axisXSignalFeedback,
                lineChartSignalCorrelation, textFieldResultTimeStamp,
                textFieldResultRealDistance, textFieldResultCalculatedDistance);
    }

    public void pauseAnimation() {
        isAnimationPaused.set(true);
    }

    public void repauseAnimation() {
        isAnimationPaused.set(false);
    }

    public void stopAnimation() {
        isAnimationRunning.set(false);
    }

    private void run(Environment environment,
                     LineChart chartSignalProbe,
                     LineChart chartSignalFeedback,
                     LineChart chartCorrelation,
                     NumberAxis axisXSignalProbe,
                     NumberAxis axisXSignalFeedback,
                     NumberAxis lineChartSignalCorrelation,
                     TextField textFieldResultTimeStamp,
                     TextField textFieldResultRealDistance,
                     TextField textFieldResultCalculatedDistance) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                while (isAnimationRunning.get()) {
                    long callTimePeriod = 0;
                    if (!isAnimationPaused.get()) {
                        callTimePeriod += action(() -> environment.step());
                        callTimePeriod += action(() -> {
                            Platform.runLater(() -> {
                                textFieldSetValue(textFieldResultTimeStamp,
                                        String.valueOf(environment.getTimestamp()));
                                textFieldSetValue(textFieldResultRealDistance,
                                        String.valueOf(environment.getItemDistance()));
                                textFieldSetValue(textFieldResultCalculatedDistance,
                                        String.valueOf(environment.getDistanceSensor()
                                                .getDistance()));

                                DiscreteSignal probeSignal = environment.getDistanceSensor()
                                        .getDiscreteProbeSignal();
                                DiscreteSignal feedbackSignal = environment.getDistanceSensor()
                                        .getDiscreteFeedbackSignal();
                                DiscreteSignal correlationSignal = environment.getDistanceSensor()
                                        .getCorrelationSignal();

                                updateNumberAxis(axisXSignalProbe, probeSignal.getRangeStart(),
                                        probeSignal.getRangeStart()
                                                + probeSignal.getRangeLength(),
                                        AXIS_TICK_UNIT);
                                updateNumberAxis(axisXSignalFeedback,
                                        feedbackSignal.getRangeStart(),
                                        feedbackSignal.getRangeStart()
                                                + probeSignal.getRangeLength(),
                                        AXIS_TICK_UNIT);
                                updateNumberAxis(lineChartSignalCorrelation,
                                        correlationSignal.getRangeStart(),
                                        correlationSignal.getRangeStart()
                                                + correlationSignal.getRangeLength(),
                                        AXIS_TICK_UNIT);

                                representSignal(chartSignalProbe, probeSignal);
                                representSignal(chartSignalFeedback, feedbackSignal);
                                representSignal(chartCorrelation, correlationSignal);
                            });
                        });
                    }

                    /*----- Counting sleep time in ms and converting TimeStep to ms -----*/
                    long sleepTime = ((long) (environment.getTimeStep() * 1000)) - callTimePeriod;
                    try {
                        TimeUnit.MILLISECONDS.sleep(sleepTime > 0 ? sleepTime : 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        thread = new Thread(task);
        thread.start();
    }

    private void representSignal(LineChart lineChart, Signal signal) {
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

        System.out.println("Wygenerowanu punktów: " + data.size());

        List<ChartRecord<Number, Number>> chartData = data
                .stream()
                .map(d -> new ChartRecord<Number, Number>(d.getX(), d.getY()))
                .collect(Collectors.toList());

        clearAndFillLineChart(lineChart, chartData);
    }

    private long action(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();

        return (System.currentTimeMillis() - startTime);
    }
}
    
