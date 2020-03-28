package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.Signal;

public class DAC {

    public ContinuousSignal zeroOrderHold(Signal signal) {
        return new ContinuousSignal(signal.getRangeStart(), signal.getRangeLength()) {
            @Override
            public double value(double t) {
                int index =
                        (int) Math.floor((t - signal.getRangeStart()) / signal.getRangeLength()
                                * (signal.getData().size() - 1));
                return signal.getData().get(index).getY();
            }
        };
    }

    public ContinuousSignal firstOrderHold(Signal signal) {
        return new ContinuousSignal(signal.getRangeStart(), signal.getRangeLength()) {
            @Override
            public double value(double t) {
                int index =
                        (int) Math.floor((t - signal.getRangeStart()) / signal.getRangeLength()
                                * (signal.getData().size() - 1));
                Data A = signal.getData().get(index);
                if (index < signal.getData().size() - 1) {
                    Data B = signal.getData().get(index + 1);
                    return (t - A.getX()) / (B.getX() - A.getX()) * (B.getY() - A.getY()) + A.getY();
                } else {
                    return A.getY();
                }
            }
        };
    }
}
