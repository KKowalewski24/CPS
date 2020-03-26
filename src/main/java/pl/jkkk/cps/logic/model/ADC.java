package pl.jkkk.cps.logic.model;

import java.util.ArrayList;
import java.util.List;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.FixedSignal;
import pl.jkkk.cps.logic.model.signal.Signal;

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
        return new FixedSignal(data);
    }

    public Signal roundingQuantization(Signal signal, int numberOfLevels) {
        List<Double> levels = calculateLevels(signal, numberOfLevels);
        List<Data> data = new ArrayList<>();
        signal.getData().forEach(sample -> {
            data.add(new Data(
                        sample.getX(),
                        levels.stream().sorted((level1, level2) -> 
                            Double.compare(Math.abs(sample.getY() - level1), 
                                Math.abs(sample.getY() - level2))).findFirst().get()
                    ));
        });
        return new FixedSignal(data);
    }

    public Signal truncatingQuantization(Signal signal, int numberOfLevels) {
        List<Double> levels = calculateLevels(signal, numberOfLevels);
        List<Data> data = new ArrayList<>();
        signal.getData().forEach(sample -> {
            data.add(new Data(
                        sample.getX(),
                        levels.stream().sorted((level1, level2) -> {
                            /* first lower, second not */
                            if(level1 < sample.getY() && level2 >= sample.getY()) return 1;
                            /* second lower, first not */
                            else if(level1 >= sample.getY() && level2 < sample.getY()) return -1;
                            /* both the same */
                            else return Double.compare(Math.abs(sample.getY() - level1), 
                                Math.abs(sample.getY() - level2));
                        }).findFirst().get()
                    ));
        });
        return new FixedSignal(data);
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
