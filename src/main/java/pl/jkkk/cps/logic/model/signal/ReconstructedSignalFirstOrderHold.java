package pl.jkkk.cps.logic.model.signal;

import java.util.List;

import pl.jkkk.cps.logic.model.Data;

public class ReconstructedSignalFirstOrderHold extends ContinuousSignal{
    
    private final List<Data> sourceData;

    public ReconstructedSignalFirstOrderHold(Signal sourceSignal) {
       super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength());
       this.sourceData = sourceSignal.getData();
    }

    @Override
    public double value(double t) {
        int index = (int) Math.floor((t - rangeStart) / rangeLength * (sourceData.size() - 1));
        Data A = sourceData.get(index);
        if (index < sourceData.size() - 1) {
            Data B = sourceData.get(index + 1);
            return (t - A.getX()) / (B.getX() - A.getX()) * (B.getY() - A.getY()) + A.getY();
        } else {
            return A.getY();
        }
    }
}
