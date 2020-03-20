package pl.jkkk.cps.logic.model.signal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.Range;

public abstract class DiscreteSignal implements Signal {

    protected final double rangeStart;
    protected final double rangeLength;
    protected final double sampleRate;
    protected final Data[] data;

    public DiscreteSignal(double rangeStart, double rangeLength, double sampleRate) {
        this.rangeStart = rangeStart;
        this.rangeLength = rangeLength;
        this.sampleRate = sampleRate;
        this.data = new Data[(int) (rangeLength * sampleRate)];
    }

    /* This function act as a REAL continous signal */
    abstract protected double value(double t);

    @Override
    public List<Data> generate() {
        double step = rangeLength / (data.length - 1);
        for (int i = 0; i < data.length; i++) {
            double x = i * step;
            double y = value(x);
            data[i] = new Data(x, y);
        }
        return Arrays.asList(data);
    }

    @Override
    public List<Range> generateHistogram(int numberOfRanges) {
        final double min = Arrays.asList(data).stream()
            .mapToDouble(data -> data.getY()).min().getAsDouble();
        final double max = Arrays.asList(data).stream()
            .mapToDouble(data -> data.getY()).max().getAsDouble();
        final List<Range> ranges = new ArrayList<>();
        IntStream.range(0, numberOfRanges).forEach(i -> {
            double begin = min + (max - min) / numberOfRanges * i;
            double end = min + (max - min) / numberOfRanges * (i + 1);
            int quantity = (int)Arrays.asList(data).stream()
                .filter(data -> data.getY() >= begin && data.getY() <= end)
                .count();
            ranges.add(new Range(begin, end, quantity));
        });
        return ranges;
    }

    @Override
    public double meanValue() {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i].getY();
        }
        return sum / data.length;
    }

    @Override
    public double absMeanValue() {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.abs(data[i].getY());
        }
        return sum / data.length;
    }

    @Override
    public double rmsValue() {
        return Math.sqrt(meanPowerValue());
    }

    @Override
    public double varianceValue() {
        double mean = meanValue();
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i].getY() - mean, 2.0);
        }
        return sum / data.length;
    }

    @Override
    public double meanPowerValue() {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i].getY(), 2.0);
        }
        return sum / data.length;
    }
}
