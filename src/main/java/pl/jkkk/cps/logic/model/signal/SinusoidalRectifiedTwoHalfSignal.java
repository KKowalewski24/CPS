package pl.jkkk.cps.logic.model.signal;

public class SinusoidalRectifiedTwoHalfSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;

    public SinusoidalRectifiedTwoHalfSignal(double rangeStart, double rangeLength,
                                            double amplitude, double term) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.term = term;
    }

    @Override
    public double value(double t) {
        return amplitude * Math.abs(Math.sin((2.0 * Math.PI / term) * (t - getRangeStart())));
    }
}
