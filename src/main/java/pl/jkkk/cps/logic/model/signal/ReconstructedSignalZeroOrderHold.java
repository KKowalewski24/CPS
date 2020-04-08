package pl.jkkk.cps.logic.model.signal;

import java.util.List;

import pl.jkkk.cps.logic.model.Data;

public class ReconstructedSignalZeroOrderHold extends ContinuousSignal {

    private final List<Data> sourceData;

    public ReconstructedSignalZeroOrderHold(Signal sourceSignal){
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength());
        sourceData = sourceSignal.getData();
    }

    public double value(double t) {
        int index = (int) Math.floor((t - rangeStart) / rangeLength * (sourceData.size() - 1));
        return sourceData.get(index).getY();
    }
}
