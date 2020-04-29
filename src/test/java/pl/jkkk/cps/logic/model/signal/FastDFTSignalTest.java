package pl.jkkk.cps.logic.model.signal;

import org.apache.commons.math3.complex.Complex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FastDFTSignalTest {
    
    private double[] x = {1, 2, 3, 4};

    private FastDFTSignal signal = new FastDFTSignal(
        new DiscreteSignal(0.0, x.length, 1.0){
            @Override
            public double value(int n) {
                return x[n];
            }
        });

    @Test
    public void calculateRecursiveFFT() {
        double[] x = {1, 2, 3, 4};
        double[] yr = {10, -2, -2, -2};
        double[] yi = {0.0, 2, 0.0, -2};
        for (int i = 0; i < x.length; i++) {
            Assertions.assertEquals(yr[i], signal.recursiveFFT(x, i).getReal(), 0.00000001);
            Assertions.assertEquals(yi[i], signal.recursiveFFT(x, i).getImaginary(), 0.00000001);
        }
    }

    @Test
    public void reverseBits() {
        Assertions.assertEquals(0, signal.reverseBits(0, 3));
        Assertions.assertEquals(4, signal.reverseBits(1, 3));
        Assertions.assertEquals(2, signal.reverseBits(2, 3));
        Assertions.assertEquals(6, signal.reverseBits(3, 3));
        Assertions.assertEquals(1, signal.reverseBits(4, 3));
        Assertions.assertEquals(5, signal.reverseBits(5, 3));
        Assertions.assertEquals(3, signal.reverseBits(6, 3));
        Assertions.assertEquals(7, signal.reverseBits(7, 3));
    }

    @Test
    public void mixSamples() {
        double[] x = {0, 1, 2, 3, 4, 5, 6, 7};
        double[] y = {0, 4, 2, 6, 1, 5, 3, 7};
        Assertions.assertArrayEquals(y, signal.mixSamples(x));
    }
}
