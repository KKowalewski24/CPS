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
        return null;
    }

    public List<Double> truncatingQuantization(List<Double> samples, int numberOfLevels){
        return null;
    } 
}
