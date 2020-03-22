package pl.jkkk.cps.logic.model.signal;

import pl.jkkk.cps.logic.model.Data;

public abstract class DiscreteSignal extends Signal {

    protected final double rangeStart;
    protected final double rangeLength;
    protected final double sampleRate;

    public DiscreteSignal(double rangeStart, double rangeLength, double sampleRate) {
        super((int) (rangeLength * sampleRate));
        this.rangeStart = rangeStart;
        this.rangeLength = rangeLength;
        this.sampleRate = sampleRate;
    }

    /* This function act as a REAL continous signal */
    protected abstract double value(double t);

    @Override
    public void generate() {
        double step = rangeLength / (data.length - 1);
        for (int i = 0; i < data.length; i++) {
            double x = i * step + rangeStart;
            double y = value(x);
            data[i] = new Data(x, y);
        }
    }

    public double getRangeStart() {
        return rangeStart;
    }

    public double getRangeLength() {
        return rangeLength;
    }
}
