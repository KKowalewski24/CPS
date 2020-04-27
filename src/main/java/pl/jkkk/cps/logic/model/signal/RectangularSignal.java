package pl.jkkk.cps.logic.model.signal;

public class RectangularSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;
    private final double fulfillment;

    public RectangularSignal(double rangeStart, double rangeLength,
                             double amplitude, double term, double fulfillment) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.term = term;
        this.fulfillment = fulfillment;
    }

    @Override
    public double value(double t) {
        if (((t - getRangeStart()) / term)
                - Math.floor((t - getRangeStart()) / term) < fulfillment) {
            return amplitude;
        } else {
            return 0.0;
        }
    }
}
