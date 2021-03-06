package pl.jkkk.cps.logic.model.signal;

public class UnitJumpSignal extends ContinuousSignal {

    private final double amplitude;
    private final double jumpPosition;

    public UnitJumpSignal(double rangeStart, double rangeLength,
                          double amplitude, double jumpPosition) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.jumpPosition = jumpPosition;
    }

    @Override
    public double value(double t) {
        if (t > jumpPosition) {
            return amplitude;
        } else if (t == jumpPosition) {
            return 0.5 * amplitude;
        } else {
            return 0.0;
        }
    }
}
