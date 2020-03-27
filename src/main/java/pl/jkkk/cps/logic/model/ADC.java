package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.Signal;

import java.util.ArrayList;
import java.util.List;

public class ADC {

    public Signal sampling(ContinuousSignal signal, int sampleRate) {
        int numberOfSamples = (int) (signal.getRangeLength() * sampleRate);
        List<Data> data = new ArrayList<>();
        Double step = signal.getRangeLength() / (numberOfSamples - 1);
        for (int i = 0; i < numberOfSamples; i++) {
            double x = i * step + signal.getRangeStart();
            double y = signal.value(x);
            data.add(new Data(x, y));
        }
        return createSignalBasingOnExistingData(data);
    }

    public Signal roundingQuantization(Signal signal, int numberOfLevels) {
        List<Double> levels = calculateLevels(signal, numberOfLevels);
        List<Data> data = new ArrayList<>();
        signal.getData().forEach(sample -> {
            int index = (int) Math.round(
                    (sample.getY() - levels.get(0))
                            / (levels.get(levels.size() - 1) - levels.get(0))
                            * (levels.size() - 1)
            );
            data.add(new Data(sample.getX(), levels.get(index)));
        });
        return createSignalBasingOnExistingData(data);
    }

    public Signal truncatingQuantization(Signal signal, int numberOfLevels) {
        List<Double> levels = calculateLevels(signal, numberOfLevels);
        List<Data> data = new ArrayList<>();
        signal.getData().forEach(sample -> {
            int index = (int) Math.floor(
                    (sample.getY() - levels.get(0))
                            / (levels.get(levels.size() - 1) - levels.get(0))
                            * (levels.size() - 1)
            );
            data.add(new Data(sample.getX(), levels.get(index)));
        });
        return createSignalBasingOnExistingData(data);
    }

    private Signal createSignalBasingOnExistingData(List<Data> existingData) {
        return new Signal(existingData.size()) {
            @Override
            public void generate() {
                for (int i = 0; i < existingData.size(); i++) {
                    this.data[i] = existingData.get(i);
                }
            }
        };
    }

    private List<Double> calculateLevels(Signal signal, int numberOfLevels) {
        List<Double> levels = new ArrayList<>();
        double min = signal.getData().stream()
                .mapToDouble(sample -> sample.getY()).min().getAsDouble();
        double max = signal.getData().stream()
                .mapToDouble(sample -> sample.getY()).max().getAsDouble();
        double step = (max - min) / (numberOfLevels - 1);
        for (int i = 0; i < numberOfLevels; i++) {
            levels.add(step * i + min);
        }
        return levels;
    }
}
