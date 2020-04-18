package pl.jkkk.cps.logic.model.signal;

public class UnitImpulseSignal extends DiscreteSignal {

    private final double amplitude;
    private final int jumpSampleNumber;

    public UnitImpulseSignal(double rangeStart, double rangeLength,
                             double sampleRate, double amplitude, int jumpSampleNumber) {
        super(rangeStart, rangeLength, sampleRate);
        this.amplitude = amplitude;
        this.jumpSampleNumber = jumpSampleNumber;
    }

    @Override
    public double value(int i) {
        if (i == jumpSampleNumber) {
            return amplitude;
        } else {
            return 0.0;
        }
    }
}
