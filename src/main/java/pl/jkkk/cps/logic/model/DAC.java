package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;

public class DAC {
    
    public ContinuousSignal zeroOrderHold(DiscreteSignal signal){
        return new ContinuousSignal(signal.getRangeStart(), signal.getRangeLength()){
            @Override
            public double value(double t){
                int index = (int) Math.floor((t - signal.getRangeStart()) / signal.getRangeLength() * 
                        signal.getData().size());
                return signal.getData().get(index).getY();
            }
        };
    }
}
