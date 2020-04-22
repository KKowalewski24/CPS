package pl.jkkk.cps.logic.model.simulator;

import pl.jkkk.cps.logic.model.signal.ContinuousBasedDiscreteSignal;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.CorrelationSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.SinusoidalSignal;

public class DistanceSensor {

    private final ContinuousSignal probeSignal;
    private final double probeSignalTerm;
    private final double sampleRate;
    private final int bufferLength;
    private final double reportTerm;
    private final double signalVelocity;

    private DiscreteSignal discreteProbeSignal;
    private DiscreteSignal discreteFeedbackSignal;
    private DiscreteSignal correlationSignal;
    private double lastCalculationTimestamp = -Double.MAX_VALUE;
    private double distance;

    public DistanceSensor(double probeSignalTerm, double sampleRate,
                          int bufferLength, double reportTerm, double signalVelocity) {
        this.probeSignal = new SinusoidalSignal(0.0, 0.0, 1.0, probeSignalTerm);
        this.probeSignalTerm = probeSignalTerm;
        this.sampleRate = sampleRate;
        this.bufferLength = bufferLength;
        this.reportTerm = reportTerm;
        this.signalVelocity = signalVelocity;
    }

    public ContinuousSignal generateProbeSignal() {
        /* always return new independent copy of probe signal */
        return new SinusoidalSignal(0.0, 0.0, 1.0, probeSignalTerm);
    }

    public DiscreteSignal getDiscreteProbeSignal() {
        return discreteProbeSignal;
    }

    public DiscreteSignal getDiscreteFeedbackSignal() {
        return discreteFeedbackSignal;
    }

    public DiscreteSignal getCorrelationSignal() {
        return correlationSignal;
    }

    public double getDistance() {
        return distance;
    }

    public void update(ContinuousSignal feedbackSignal, double timestamp) {
        /* fill buffers */
        double rangeStart = timestamp - bufferLength / sampleRate;
        this.discreteProbeSignal = new ContinuousBasedDiscreteSignal(rangeStart, bufferLength,
                sampleRate, probeSignal);
        this.discreteFeedbackSignal = new ContinuousBasedDiscreteSignal(rangeStart, bufferLength,
                sampleRate, feedbackSignal);

        /* check if it's time for distance calculation */
        if (timestamp - lastCalculationTimestamp >= reportTerm) {
            lastCalculationTimestamp = timestamp;
            calculateDistance();
        }
    }

    private void calculateDistance() {
        /* correlation */
        this.correlationSignal = new CorrelationSignal(discreteProbeSignal, discreteFeedbackSignal);

        /* find max */
        int indexOfFirstMax = correlationSignal.getNumberOfSamples() / 2;
        for (int i = indexOfFirstMax + 1; i < correlationSignal.getNumberOfSamples(); i++) {
            if (correlationSignal.value(i) > correlationSignal.value(indexOfFirstMax)) {
                indexOfFirstMax = i;
            }
        }

        /* calculate distance */
        double delay = (indexOfFirstMax - correlationSignal.getNumberOfSamples() / 2)
                / sampleRate;
        this.distance = delay * signalVelocity / 2.0;
    }
}
