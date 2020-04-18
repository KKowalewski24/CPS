package pl.jkkk.cps.logic.model.signal;

public class TriangularSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;
    private final double fulfillment;

    public TriangularSignal(double rangeStart, double rangeLength,
                            double amplitude, double term, double fulfillment) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.term = term;
        this.fulfillment = fulfillment;
    }

    @Override
    public double value(double t) {
        double termPosition = ((t - getRangeStart()) / term)
                - Math.floor((t - getRangeStart()) / term);

        if (termPosition < fulfillment) {
            return termPosition / fulfillment * amplitude;
        } else {
            return (1 - (termPosition - fulfillment) / (1 - fulfillment)) * amplitude;
        }
    }
}
