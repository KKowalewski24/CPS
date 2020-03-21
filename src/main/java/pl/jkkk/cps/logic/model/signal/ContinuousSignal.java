package pl.jkkk.cps.logic.model.signal;

public abstract class ContinuousSignal extends DiscreteSignal {

    private static final int NUMBER_OF_SAMPLES = 1000000;

    public ContinuousSignal(double rangeStart, double rangeLength) {
        super(rangeStart, rangeLength, NUMBER_OF_SAMPLES / rangeLength);
    }

    @Override
    abstract public double value(double t);
}
