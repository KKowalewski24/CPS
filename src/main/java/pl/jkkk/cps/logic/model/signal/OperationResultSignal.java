package pl.jkkk.cps.logic.model.signal;

import java.util.ArrayList;
import java.util.List;

import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.Operation;

public class OperationResultSignal extends Signal {

    private final Signal s1;
    private final Signal s2;
    private final Operation operation;

    public OperationResultSignal(Signal s1, Signal s2, Operation operation) {
        super(s1.getRangeStart(), s1.getRangeLength());
        this.s1 = s1;
        this.s2 = s2;
        this.operation = operation;
    }

    @Override
    public List<Data> generateDiscreteRepresentation() {
        List<Data> data1 = s1.generateDiscreteRepresentation();
        List<Data> data2 = s2.generateDiscreteRepresentation();
        List<Data> resultData = new ArrayList<>();

        for (int i = 0; i < data1.size(); i++) {
            resultData
                    .add(new Data(data1.get(i).getX(), operation.operation(data1.get(i).getY(), data2.get(i).getY())));
        }
        return resultData;
    }
}
