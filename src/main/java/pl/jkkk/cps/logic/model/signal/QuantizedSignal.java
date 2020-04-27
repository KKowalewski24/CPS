package pl.jkkk.cps.logic.model.signal;

import java.util.ArrayList;
import java.util.List;

public abstract class QuantizedSignal extends DiscreteSignal {

    private final List<Double> levels;
    private final DiscreteSignal sourceSignal;

    public QuantizedSignal(DiscreteSignal sourceSignal, int numberOfLevels) {
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength(),
                sourceSignal.getSampleRate());
        this.levels = calculateLevels(sourceSignal, numberOfLevels);
        this.sourceSignal = sourceSignal;
    }

    public abstract double rounding(double x);

    @Override
    public double value(int i) {
        int levelIndex = (int) rounding((sourceSignal.value(i) - levels.get(0))
                / (levels.get(levels.size() - 1) - levels.get(0)) * (levels.size() - 1));
        return levels.get(levelIndex);
    }

    private final List<Double> calculateLevels(DiscreteSignal signal, int numberOfLevels) {
        List<Double> levels = new ArrayList<>();
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            double y = signal.value(i);
            if (y > max) {
                max = y;
            }
            if (y < min) {
                min = y;
            }
        }
        double step = (max - min) / (numberOfLevels - 1);
        for (int i = 0; i < numberOfLevels; i++) {
            levels.add(step * i + min);
        }
        return levels;
    }
}
