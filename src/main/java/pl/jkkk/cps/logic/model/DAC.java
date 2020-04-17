package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.ReconstructedSignalFirstOrderHold;
import pl.jkkk.cps.logic.model.signal.ReconstructedSignalSincBasic;
import pl.jkkk.cps.logic.model.signal.ReconstructedSignalZeroOrderHold;

public class DAC {

    public ContinuousSignal zeroOrderHold(DiscreteSignal signal) {
        return new ReconstructedSignalZeroOrderHold(signal);
    }

    public ContinuousSignal firstOrderHold(DiscreteSignal signal) {
        return new ReconstructedSignalFirstOrderHold(signal);
    }

    public ContinuousSignal sincBasic(DiscreteSignal signal, int N) {
        return new ReconstructedSignalSincBasic(signal, N);
    }
}
