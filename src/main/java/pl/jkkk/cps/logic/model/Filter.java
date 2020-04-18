package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.ConvolutionSignal;

public class Filter {
    public DiscreteSignal filter(DiscreteSignal signal, DiscreteSignal filter) {
        return new ConvolutionSignal(filter, signal);
    }
}
