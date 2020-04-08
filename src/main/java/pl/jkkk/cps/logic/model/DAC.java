package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.ReconstructedSignalZeroOrderHold;
import pl.jkkk.cps.logic.model.signal.ReconstructedSignalSincBasic;
import pl.jkkk.cps.logic.model.signal.ReconstructedSignalFirstOrderHold;
import pl.jkkk.cps.logic.model.signal.Signal;

public class DAC {

    public ContinuousSignal zeroOrderHold(Signal signal) {
        return new ReconstructedSignalZeroOrderHold(signal);
    }

    public ContinuousSignal firstOrderHold(Signal signal) {
        return new ReconstructedSignalFirstOrderHold(signal);
    }

    public ContinuousSignal sincBasic(Signal signal, int N) {
        return new ReconstructedSignalSincBasic(signal, N);
    }
}
