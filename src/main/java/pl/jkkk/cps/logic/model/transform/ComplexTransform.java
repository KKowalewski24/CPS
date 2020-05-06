package pl.jkkk.cps.logic.model.transform;

import org.apache.commons.math3.complex.Complex;

import java.io.Serializable;

public abstract class ComplexTransform {

    public abstract Complex[] transform(Complex[] x);

    public Complex[] transform(double[] x) {
        Complex[] y = new Complex[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = new Complex(x[i]);
        }
        return transform(y);
    }
}
