package pl.jkkk.cps.logic.model.signal;

import pl.jkkk.cps.logic.model.Operation;

public class OperationResultDiscreteSignal extends DiscreteSignal {

    private final DiscreteSignal s1;
    private final DiscreteSignal s2;
    private final Operation operation;

    public OperationResultDiscreteSignal(DiscreteSignal s1, DiscreteSignal s2, 
            Operation operation) {
        super(s1.getRangeStart(), s1.getNumberOfSamples(), s1.getSampleRate());
        this.s1 = s1;
        this.s2 = s2;
        this.operation = operation;
    }

    @Override
    public double value(int n) {
        return operation.operation(s1.value(n), s2.value(n));
    }
}
