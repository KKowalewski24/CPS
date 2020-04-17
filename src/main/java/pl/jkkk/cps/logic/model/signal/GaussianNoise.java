package pl.jkkk.cps.logic.model.signal;

import java.util.Random;

public class GaussianNoise extends ContinuousSignal {

    private final double amplitude;
    private final Random rand;

    public GaussianNoise(double rangeStart, double rangeLength, double amplitude) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.rand = new Random(47);
    }

    @Override
    public double value(double t) {
        return (rand.nextGaussian() * 2.0 - 1.0) * amplitude;
    }
}
