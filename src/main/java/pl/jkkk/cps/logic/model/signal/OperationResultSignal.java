package pl.jkkk.cps.logic.model.signal;

import java.util.Arrays;
import java.util.List;

import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.Operation;

public class OperationResultSignal extends Signal{
    
    private final Signal s1;
    private final Signal s2;
    private final Operation operation;

    public OperationResultSignal(Signal s1, Signal s2, Operation operation){
        super(s1.getData().size());

        if(s1.getData().size() != s2.getData().size())
            throw new NotSameLengthException();

        this.s1 = s1;
        this.s2 = s2;
        this.operation = operation;
    }

    @Override
    public void generate(){
        s1.generate();
        s2.generate();

        for(int i = 0; i < data.length; i++){
            data[i] = new Data(s1.getData().get(i).getX(), 
                operation.operation(s1.getData().get(i).getY(), 
                    s2.getData().get(i).getY()));
        }
    }

    public class NotSameLengthException extends RuntimeException{}
}
