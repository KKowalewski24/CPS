package pl.jkkk.cps.logic.model.signal;

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
