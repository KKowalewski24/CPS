package pl.jkkk.cps.logic.model.signal;

public abstract class ContinuousSignal extends DiscreteSignal {

    protected static final int NUMBER_OF_SAMPLES = 1000;

    public ContinuousSignal(double rangeStart, double rangeLength) {
        super(rangeStart, rangeLength, NUMBER_OF_SAMPLES / rangeLength);
    }
}
