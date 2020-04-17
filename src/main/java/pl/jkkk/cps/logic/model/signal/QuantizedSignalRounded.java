package pl.jkkk.cps.logic.model.signal;

public class QuantizedSignalRounded extends QuantizedSignal {

    public QuantizedSignalRounded(final DiscreteSignal sourceSignal, final int numberOfLevels) {
        super(sourceSignal, numberOfLevels);
    }

    @Override
    public double rounding(final double x) {
        return Math.round(x);
    }
}
