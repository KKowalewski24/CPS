package pl.jkkk.cps.logic.model.simulator;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;

public class Environment {

    private final double timeStep;
    private final double signalVelocity;
    private final double itemVelocity;
    private final DistanceSensor distanceSensor;

    private double itemDistance;
    private double timestamp = 0.0;

    public Environment(double timeStep, double signalVelocity, double itemVelocity,
                       DistanceSensor distanceSensor, double startItemDistance) {
        this.timeStep = timeStep;
        this.signalVelocity = signalVelocity;
        this.itemVelocity = itemVelocity;
        this.distanceSensor = distanceSensor;
        this.itemDistance = startItemDistance;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public double getSignalVelocity() {
        return signalVelocity;
    }

    public double getItemVelocity() {
        return itemVelocity;
    }

    public DistanceSensor getDistanceSensor() {
        return distanceSensor;
    }

    public double getItemDistance() {
        return itemDistance;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void step() {
        /* update timestamp */
        timestamp += timeStep;

        /* update item position */
        itemDistance += itemVelocity * timeStep;

        /* retrieve feedback signal from probeSignal */
        double delay = itemDistance / signalVelocity * 2.0;
        ContinuousSignal probeSignal = distanceSensor.generateProbeSignal();
        probeSignal.setRangeStart(probeSignal.getRangeStart() + delay);
        ContinuousSignal feedbackSignal = probeSignal;

        /* update distance sensor */
        distanceSensor.update(feedbackSignal, timestamp);
    }
}
