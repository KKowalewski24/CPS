package pl.jkkk.cps.logic.model.signal;

import java.util.Random;

public class UniformNoise extends ContinuousSignal {

    private final double amplitude;
    private final Random rand;

    public UniformNoise(double rangeStart, double rangeLength, double amplitude) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.rand = new Random(47);
    }

    @Override
    public double value(double t) {
        return (rand.nextDouble() * 2.0 - 1.0) * amplitude;
    }
}
