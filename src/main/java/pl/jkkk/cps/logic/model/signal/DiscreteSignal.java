package pl.jkkk.cps.logic.model.signal;

import java.util.ArrayList;
import java.util.List;

import pl.jkkk.cps.logic.model.Data;

public abstract class DiscreteSignal extends Signal {

    private final double sampleRate;
    private final int numberOfSamples;

    /**
     * This constructor initiate numberOfSamples basing on rangeLength,
     * in that case rangeLength can be a little bit greater than x-distance
     * between first and last sample.
     */
    public DiscreteSignal(double rangeStart, double rangeLength, double sampleRate) {
        super(rangeStart, rangeLength);
        this.sampleRate = sampleRate;
        /* how many whole samples does this rangeLength contain */
        this.numberOfSamples = (int) (rangeLength * sampleRate);
    }

    /**
     * This constructor initiate rangeLength basing on numberOfSamples,
     * in that case rangeLength always equals x-distance between first
     * and last sample.
     */
    public DiscreteSignal(double rangeStart, int numberOfSamples, double sampleRate) {
        super(rangeStart, numberOfSamples * (1.0 / sampleRate));
        this.sampleRate = sampleRate;
        this.numberOfSamples = numberOfSamples;
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public abstract double value(int i);

    public double argument(int i) {
        return i * (1.0 / sampleRate) + getRangeStart();
    }

    @Override
    public List<Data> generateDiscreteRepresentation() {
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < numberOfSamples; i++) {
            data.add(new Data(argument(i), value(i)));
        }
        return data;
    }
}
