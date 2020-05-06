package pl.jkkk.cps.logic.model.signal;

public class StaticDiscreteSignal extends DiscreteSignal {

    private final double[] samples;

    public StaticDiscreteSignal(final double[] samples, final double sampleRate) {
        super(0.0, samples.length, sampleRate);
        this.samples = samples;
    }

    @Override
    public double value(final int i) {
        return samples[i];
    }
}
