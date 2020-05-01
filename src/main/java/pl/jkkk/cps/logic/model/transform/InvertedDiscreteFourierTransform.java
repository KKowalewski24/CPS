package pl.jkkk.cps.logic.model.transform;

import org.apache.commons.math3.complex.Complex;

public class InvertedDiscreteFourierTransform extends Transform {

    @Override
    public Complex[] transform(Complex[] x) {
        int N = x.length;
        double Warg = 2.0 * Math.PI / N;
        Complex W = new Complex(Math.cos(Warg), Math.sin(Warg));

        Complex[] X = new Complex[N];

        for (int m = 0; m < N; m++) {
            Complex sum = new Complex(0.0);
            for (int n = 0; n < N; n++) {
                sum = sum.add(x[n].multiply(W.pow(m * n)));
            }
            X[m] = sum;
        }

        return X;
    }
}
