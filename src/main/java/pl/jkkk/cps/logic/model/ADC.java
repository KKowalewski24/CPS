package pl.jkkk.cps.logic.model;

import java.util.ArrayList;
import java.util.List;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;

public class ADC {

    public List<Double> sampling(ContinuousSignal signal, int sampleRate){
        int numberOfSamples = (int)(signal.getRangeLength() * sampleRate);
        List<Double> data = new ArrayList<>();
        Double step = signal.getRangeLength() / (numberOfSamples - 1);
        for(int i = 0; i < numberOfSamples; i++){
            data.add(signal.value(i * step + signal.getRangeStart()));
        }
        return data;
    }

    public List<Double> roundingQuantization(List<Double> samples, int numberOfLevels){
        final List<Double> levels = new ArrayList<>();
        double min = samples.stream().mapToDouble(x -> x.doubleValue()).min().getAsDouble();
        double max = samples.stream().mapToDouble(x -> x.doubleValue()).max().getAsDouble();
        double step = (max - min) / (numberOfLevels - 1);
        for(int i = 0; i < numberOfLevels; i++){
            levels.add(step * i);
        }

        final List<Double> data = new ArrayList<>();
        samples.forEach(sample -> {
            data.add(levels.stream().sorted((level1, level2) -> {
                double d1 = Math.abs(sample - level1);
                double d2 = Math.abs(sample - level2);
                if(d1 < d2) return 1;
                else if(d1 > d2) return -1;
                else return 0;
            }).findFirst().get());
        });
        return data;
    }

    public List<Double> truncatingQuantization(List<Double> samples, int numberOfLevels){
        return null;
    } 
}
