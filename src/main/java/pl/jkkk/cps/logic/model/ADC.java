package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.QuantizedSignalRounded;
import pl.jkkk.cps.logic.model.signal.QuantizedSignalTruncated;

public class ADC {

    public DiscreteSignal sampling(ContinuousSignal signal, double sampleRate) {
        return new DiscreteSignal(signal.getRangeStart(), signal.getRangeLength(), sampleRate, signal);
    }

    public DiscreteSignal roundingQuantization(DiscreteSignal signal, int numberOfLevels) {
        return new QuantizedSignalRounded(signal, numberOfLevels);
    }

    public DiscreteSignal truncatingQuantization(DiscreteSignal signal, int numberOfLevels) {
        return new QuantizedSignalTruncated(signal, numberOfLevels);
    }
}
