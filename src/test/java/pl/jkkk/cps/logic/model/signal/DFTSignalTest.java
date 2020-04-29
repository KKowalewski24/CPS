package pl.jkkk.cps.logic.model.signal;

import org.apache.commons.math3.complex.Complex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DFTSignalTest {

    @Test
    public void testDFT() {
        double[] x = {1, 2, 3, 4};
        DFTSignal signal = new DFTSignal(
                new DiscreteSignal(0.0, x.length, 1.0){
                    @Override
                    public double value(int n) {
                        return x[n];
                    }
                });

        double[] yr = {2.5, -0.5, -0.5, -0.5};
        double[] yi = {0.0, 0.5, 0.0, -0.5};
        Complex[] dft = signal.calculate();
        for (int i = 0; i < dft.length; i++) {
            Assertions.assertEquals(yr[i], dft[i].getReal(), 0.00000001);
            Assertions.assertEquals(yi[i], dft[i].getImaginary(), 0.00000001);
        }
    }
}
