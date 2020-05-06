package pl.jkkk.cps.logic.model.signal;

import org.apache.commons.math3.complex.Complex;

public class StaticDiscreteComplexSignal extends DiscreteComplexSignal {

    private final Complex[] samples;

    public StaticDiscreteComplexSignal(final Complex[] samples, final double sampleRate) {
        super(0.0, samples.length, sampleRate);
        this.samples = samples;
    }

    @Override
    public Complex value(final int n) {
        return samples[n];
    }
}
