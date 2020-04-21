package pl.jkkk.cps.logic.model.signal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import pl.jkkk.cps.logic.exception.NotSameLengthException;
import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.Range;

public abstract class Signal implements Serializable {

    private double rangeStart;
    private final double rangeLength;

    public Signal(double rangeStart, double rangeLength) {
        this.rangeStart = rangeStart;
        this.rangeLength = rangeLength;
    }

    public double getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(double rangeStart) {
        this.rangeStart = rangeStart;
    }

    public double getRangeLength() {
        return rangeLength;
    }

    /**
     * This method returns discrete representation of
     * signal (every kind of signal, also continuous one),
     * this representation can be used to render chart,
     * compute some signal params and compare signals.
     *
     * @return list of data (2D-point) objects representing this signal in discrete way
     */
    public abstract List<Data> generateDiscreteRepresentation();

    /* compute histogram */

    public static List<Range> generateHistogram(int numberOfRanges,
                                                List<Data> discreteRepresentation) {
        final double min = discreteRepresentation.stream()
                .mapToDouble(data -> data.getY())
                .min()
                .getAsDouble();
        final double max = discreteRepresentation.stream()
                .mapToDouble(data -> data.getY())
                .max()
                .getAsDouble();
        final List<Range> ranges = new ArrayList<>();
        IntStream.range(0, numberOfRanges).forEach(i -> {
            double begin = min + (max - min) / numberOfRanges * i;
            double end = min + (max - min) / numberOfRanges * (i + 1);
            int quantity = (int) discreteRepresentation
                    .stream()
                    .filter(data -> data.getY() >= begin && data.getY() <= end)
                    .count();
            ranges.add(new Range(begin, end, quantity));
        });
        return ranges;
    }

    /* compute params */

    public static double meanValue(List<Data> discreteRepresentation) {
        double sum = 0;
        for (int i = 0; i < discreteRepresentation.size(); i++) {
            sum += discreteRepresentation.get(i).getY();
        }
        return sum / discreteRepresentation.size();
    }

    public static double absMeanValue(List<Data> discreteRepresentation) {
        double sum = 0;
        for (int i = 0; i < discreteRepresentation.size(); i++) {
            sum += Math.abs(discreteRepresentation.get(i).getY());
        }
        return sum / discreteRepresentation.size();
    }

    public static double rmsValue(List<Data> discreteRepresentation) {
        return Math.sqrt(meanPowerValue(discreteRepresentation));
    }

    public static double varianceValue(List<Data> discreteRepresentation) {
        double mean = meanValue(discreteRepresentation);
        double sum = 0;
        for (int i = 0; i < discreteRepresentation.size(); i++) {
            sum += Math.pow(discreteRepresentation.get(i).getY() - mean, 2.0);
        }
        return sum / discreteRepresentation.size();
    }

    public static double meanPowerValue(List<Data> discreteRepresentation) {
        double sum = 0;
        for (int i = 0; i < discreteRepresentation.size(); i++) {
            sum += Math.pow(discreteRepresentation.get(i).getY(), 2.0);
        }
        return sum / discreteRepresentation.size();
    }

    /* compute differences */

    public static double meanSquaredError(List<Data> result, List<Data> origin) {
        if (result.size() != origin.size()) {
            throw new NotSameLengthException();
        }

        double sum = 0.0;
        for (int i = 0; i < result.size(); i++) {
            sum += Math.pow(result.get(i).getY() - origin.get(i).getY(), 2.0);
        }

        return sum / result.size();
    }

    public static double signalToNoiseRatio(List<Data> result, List<Data> origin) {
        if (result.size() != origin.size()) {
            throw new NotSameLengthException();
        }

        double resultSquaredSum = 0.0;
        double diffSquaredSum = 0.0;
        for (int i = 0; i < result.size(); i++) {
            resultSquaredSum += Math.pow(result.get(i).getY(), 2.0);
            diffSquaredSum += Math.pow(result.get(i).getY() - origin.get(i).getY(), 2.0);
        }

        return 10.0 * Math.log10(resultSquaredSum / diffSquaredSum);
    }

    public static double peakSignalToNoiseRatio(List<Data> result, List<Data> origin) {
        if (result.size() != origin.size()) {
            throw new NotSameLengthException();
        }

        double resultMax = Double.MIN_VALUE;
        double diffSquaredSum = 0.0;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getY() > resultMax) {
                resultMax = result.get(i).getY();
            }
            diffSquaredSum += Math.pow(result.get(i).getY() - origin.get(i).getY(), 2.0);
        }

        return 10.0 * Math.log10(resultMax / (diffSquaredSum / result.size()));
    }

    public static double maximumDifference(List<Data> result, List<Data> origin) {
        if (result.size() != origin.size()) {
            throw new NotSameLengthException();
        }

        double maxDiff = Double.MIN_VALUE;
        for (int i = 0; i < result.size(); i++) {
            double diff = Math.abs(result.get(i).getY() - origin.get(i).getY());
            if (diff > maxDiff) {
                maxDiff = diff;
            }
        }

        return maxDiff;
    }

    public static double effectiveNumberOfBits(List<Data> result, List<Data> origin) {
        return (signalToNoiseRatio(result, origin) - 1.76) / 6.02;
    }
}
