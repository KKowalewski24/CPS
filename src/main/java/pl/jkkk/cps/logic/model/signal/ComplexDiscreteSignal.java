package pl.jkkk.cps.logic.model.signal;

import java.util.ArrayList;
import java.util.List;

import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.ComplexData;
import org.apache.commons.math3.complex.Complex;

public abstract class ComplexDiscreteSignal extends Signal{

    private final double sampleRate;
    private final int numberOfSamples;

    /**
     * This constructor initiate rangeLength basing on numberOfSamples,
     * in that case rangeLength always equals x-distance between first
     * and last sample.
     */
    public ComplexDiscreteSignal(double rangeStart, int numberOfSamples, double sampleRate) {
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

    public abstract Complex value(int n);

    public double argument(int n) {
        return n * (1.0 / sampleRate) + getRangeStart();
    }

    @Override
    public List<Data> generateDiscreteRepresentation() {
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < numberOfSamples; i++) {
            data.add(new Data(argument(i), value(i).abs()));
        }
        return data;
    }

    public List<ComplexData> generateComplexDiscreteRepresentation() {
        List<ComplexData> data = new ArrayList<>();
        for (int i = 0; i < numberOfSamples; i++) {
            data.add(new ComplexData(argument(i), value(i)));
        }
        return data;
    }
}
