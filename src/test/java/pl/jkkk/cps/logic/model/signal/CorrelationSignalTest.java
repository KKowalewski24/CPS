package pl.jkkk.cps.logic.model.signal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CorrelationSignalTest {

    @Test
    public void testConvolutionOperation(){
        double[] h = {1, 2, 3, 4};
        double[] x = {5, 6, 7};
        CorrelationSignal signal = new CorrelationSignal(
                new DiscreteSignal(0.0, h.length, 1.0){
                    @Override
                    public double value(int n) {
                        return h[n];
                    }
                },
                new DiscreteSignal(0.0, x.length, 1.0) {
                    @Override
                    public double value(int n) {
                        return x[n];
                    }
                });
        double[] hx = {7, 20, 38, 56, 39, 20};
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            Assertions.assertEquals(hx[i], signal.value(i));
        }
    }
}
