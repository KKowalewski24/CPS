package pl.jkkk.cps.logic.model;

import java.util.List;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;

public class ADC {

    public List<Data> sampling(ContinuousSignal signal, int sampleRate){
        return null;
    }

    public List<Data> roundingQuantization(List<Data> samples, int numberOfLevels){
        return null;
    }

    public List<Data> truncatingQuantization(List<Data> samples, int numberOfLevels){
        return null;
    } 
}
