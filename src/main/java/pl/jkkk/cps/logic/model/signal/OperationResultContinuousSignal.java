package pl.jkkk.cps.logic.model.signal;

import pl.jkkk.cps.logic.exception.NotSameLengthException;
import pl.jkkk.cps.logic.model.Operation;

public class OperationResultContinuousSignal extends ContinuousSignal {

    private final ContinuousSignal s1;
    private final ContinuousSignal s2;
    private final Operation operation;

    public OperationResultContinuousSignal(ContinuousSignal s1, ContinuousSignal s2, 
            Operation operation) {
        super(s1.getRangeStart(), s1.getRangeLength());
        this.s1 = s1;
        this.s2 = s2;
        this.operation = operation;

        if (s1.getRangeLength() != s2.getRangeLength() ||
                s1.getRangeStart() != s2.getRangeStart()) {
            throw new NotSameLengthException();
        }
    }

    @Override
    public void setRangeStart(double rangeStart) {
        super.setRangeStart(rangeStart);
        this.s1.setRangeStart(rangeStart);
        this.s2.setRangeStart(rangeStart);
    }

    @Override
    public double value(double t) {
        return operation.operation(s1.value(t), s2.value(t));
    }
}
