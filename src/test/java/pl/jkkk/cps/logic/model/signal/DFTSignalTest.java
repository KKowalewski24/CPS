package pl.jkkk.cps.logic.model.signal;

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
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            Assertions.assertEquals(yr[i], signal.value(i).getReal(), 0.00000001);
            Assertions.assertEquals(yi[i], signal.value(i).getImaginary(), 0.00000001);
        }
    }
}
