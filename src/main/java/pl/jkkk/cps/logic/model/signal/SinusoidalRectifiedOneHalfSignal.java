package pl.jkkk.cps.logic.model.signal;

public class SinusoidalRectifiedOneHalfSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;

    public SinusoidalRectifiedOneHalfSignal(double rangeStart, double rangeLength,
                                            double amplitude, double term) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.term = term;
    }

    @Override
    public double value(double t) {
        return 0.5 * amplitude
                * (Math.sin((2.0 * Math.PI / term) * (t - getRangeStart()))
                + Math.abs(Math.sin((2.0 * Math.PI / term) * (t - getRangeStart()))));
    }
}
