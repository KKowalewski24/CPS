package pl.jkkk.cps.logic.model.signal;

import java.util.ArrayList;
import java.util.List;

import pl.jkkk.cps.logic.model.Data;

public abstract class DiscreteSignal extends Signal {

    private final double sampleRate;
    private final int numberOfSamples;
    private final double step;

    public DiscreteSignal(double rangeStart, double rangeLength, double sampleRate) {
        super(rangeStart, rangeLength);
        this.sampleRate = sampleRate;
        this.numberOfSamples = (int) (rangeLength * sampleRate);
        this.step = 1.0 / sampleRate;
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    abstract public double value(int i);

    public double argument(int i) {
        return i * step + getRangeStart();
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
