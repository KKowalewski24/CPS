package pl.jkkk.cps.logic.model.signal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConvolutionSignalTest {
    
    @Test
    public void testConvolutionOperation(){
        double[] h = {1, 2, 3, 4};
        double[] x = {5, 6, 7};
        ConvolutionSignal signal = new ConvolutionSignal(
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
        double[] hx = {5, 16, 34, 52, 45, 28};
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            Assertions.assertEquals(hx[i], signal.value(i));
        }
    }
}
