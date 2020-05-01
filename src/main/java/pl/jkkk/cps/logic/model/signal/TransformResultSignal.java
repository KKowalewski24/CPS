package pl.jkkk.cps.logic.model.signal;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;

import pl.jkkk.cps.logic.model.data.ComplexData;
import pl.jkkk.cps.logic.model.data.Data;
import pl.jkkk.cps.logic.model.transform.Transform;

public class TransformResultSignal extends Signal {

    private Signal signal;
    private Transform transform;
    private Complex[] result;

    public TransformResultSignal(Signal signal, Transform transform) {
        super(0.0, 0.0);
        this.signal = signal;
        this.transform = transform;
        this.result = null;
    }

    public void generate() {
        if (signal instanceof DiscreteSignal) {
            double[] x = new double[((DiscreteSignal)signal).getNumberOfSamples()];
            for (int i = 0; i < x.length; i++) {
                x[i] = ((DiscreteSignal)signal).value(i);
            }
            this.result = transform.transform(x);
        } else if (signal instanceof TransformResultSignal) {
            Complex[] x = ((TransformResultSignal)signal).getResult();
            this.result = transform.transform(x);
        } else {
            throw new IllegalArgumentException("Can't transform for this signal type!");
        }
    }

    public Complex[] getResult() {
        if(result == null){
            throw new UnsupportedOperationException("You have to generate signal first!");
        }
        return result;
    }

    public List<ComplexData> generateComplexDiscreteRepresentation() {
        Complex[] samples = getResult();
        List<ComplexData> data = new ArrayList<>();
        for (int i = 0; i < samples.length; i++) {
            data.add(new ComplexData(i, samples[i]));
        }
        return data;
    }

    @Override
    public List<Data> generateDiscreteRepresentation() {
        throw new UnsupportedOperationException("Not available for complex signal!");
    }
}
